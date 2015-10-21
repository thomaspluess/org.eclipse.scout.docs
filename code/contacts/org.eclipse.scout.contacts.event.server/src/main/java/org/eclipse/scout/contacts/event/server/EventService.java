/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.event.server;

import java.util.UUID;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.ITableHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.TableBeanHolderFilter;
import org.eclipse.scout.contacts.event.server.sql.SQLs;
import org.eclipse.scout.contacts.event.shared.event.CreateEventPermission;
import org.eclipse.scout.contacts.event.shared.event.EventFormData;
import org.eclipse.scout.contacts.event.shared.event.EventTablePageData;
import org.eclipse.scout.contacts.event.shared.event.IEventService;
import org.eclipse.scout.contacts.event.shared.event.ReadEventPermission;
import org.eclipse.scout.contacts.event.shared.event.UpdateEventPermission;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

public class EventService implements IEventService {

  @Override
  public EventTablePageData getEventsTableData(SearchFilter filter, String companyId) throws ProcessingException {
    EventTablePageData pageData = new EventTablePageData();

    StringBuilder sqlSelect = new StringBuilder(SQLs.EVENT_PAGE_DATA_SELECT);
    StringBuilder sqlWhere = new StringBuilder(" WHERE 1 = 1 ");

    if (StringUtility.hasText(companyId)) {
      sqlWhere.append(SQLs.EVENT_PAGE_DATA_WHERE_CLAUSE);
    }

    String sql = sqlSelect.append(sqlWhere).append(SQLs.EVENT_PAGE_DATA_INTO).toString();

    SQL.selectInto(sql, new NVPair("page", pageData));

    return pageData;
  }

  @Override
  public EventFormData create(EventFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateEventPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    if (StringUtility.isNullOrEmpty(formData.getEventId())) {
      formData.setEventId(UUID.randomUUID().toString());
    }

    SQL.insert(SQLs.EVENT_INSERT, formData);

    return store(formData);
  }

  @Override
  public EventFormData load(EventFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadEventPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.selectInto(SQLs.EVENT_SELECT, formData);
    SQL.selectInto(SQLs.EVENT_PARTICIPANTS_SELECT, formData);

    return formData;
  }

  @Override
  public EventFormData prepareCreate(EventFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateEventPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    return formData;
  }

  @Override
  public EventFormData store(EventFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateEventPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    SQL.update(SQLs.EVENT_UPDATE, formData);

    TableBeanHolderFilter deletedParticipants = new TableBeanHolderFilter(formData.getParticipants(), ITableHolder.STATUS_DELETED);
    TableBeanHolderFilter insertedParticipants = new TableBeanHolderFilter(formData.getParticipants(), ITableHolder.STATUS_INSERTED);
    NVPair eventId = new NVPair("eventId", formData.getEventId());

    SQL.delete(SQLs.EVENT_PARTICIPANTS_DELETE, deletedParticipants, eventId);
    SQL.insert(SQLs.EVENT_PARTICIPANTS_INSERT, insertedParticipants, eventId);

    return formData;
  }
}