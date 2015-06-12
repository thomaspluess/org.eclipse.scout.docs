/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.widgets.client.ui.forms;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.svg.client.SVGUtility;
import org.eclipse.scout.rt.svg.client.svgfield.AbstractSvgField;
import org.eclipsescout.demo.widgets.client.ResourceBase;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.AlignedCenterField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.AlignedRightField;
import org.eclipsescout.demo.widgets.client.ui.forms.SvgFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.SvgFieldForm.MainBox.ConfigurationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SvgFieldForm.MainBox.ConfigurationBox.SvgSourceField;
import org.eclipsescout.demo.widgets.client.ui.forms.SvgFieldForm.MainBox.ConfigurationBox.UserSvgField;
import org.eclipsescout.demo.widgets.client.ui.forms.SvgFieldForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SvgFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipsescout.demo.widgets.client.ui.forms.SvgFieldForm.MainBox.SampleContentButton;
import org.w3c.dom.svg.SVGCircleElement;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGLength;

//github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo.git

public class SvgFieldForm extends AbstractForm implements IPageForm {

  public SvgFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SvgField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  /**
   * @return the AlignedCenterField
   */
  public AlignedCenterField getAlignedCenterField() {
    return getFieldByClass(AlignedCenterField.class);
  }

  /**
   * @return the AlignedRightField
   */
  public AlignedRightField getAlignedRightField() {
    return getFieldByClass(AlignedRightField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  /**
   * @return the DefaultField
   */
  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public SvgSourceField getSvgSourceField() {
    return getFieldByClass(SvgSourceField.class);
  }

  public UserSvgField getUserSvgField() {
    return getFieldByClass(UserSvgField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the SampleContentButton
   */
  public SampleContentButton getSampleContentButton() {
    return getFieldByClass(SampleContentButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    public static final String SCOUT_LOGO = "http://wiki.eclipse.org/images/e/eb/ScoutIconLarge.gif";
    public static final String BIRD = "http://2.bp.blogspot.com/_LDF9z4ZzZHo/TQZI-CUPl2I/AAAAAAAAAfc/--DuSZRxywM/s1600/bird_1008.jpg";
    public static final String BIRD_OFFLINE = "images/bird_1008.jpg";

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10.0)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10.0)
      public class DefaultField extends AbstractSvgField {

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          try {
            setSvgDocument(loadDocument("svg/SvgLogo.svg"));
          }
          catch (IOException e) {
            e.printStackTrace();
            throw new ProcessingException("Exception occured while reading svg file", e);
          }
        }
      }

      @Order(20.0)
      public class DisabledField extends AbstractSvgField {

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Disabled");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          try {
            setSvgDocument(loadDocument("svg/World_map.svg"));
          }
          catch (IOException e) {
            e.printStackTrace();
            throw new ProcessingException("Exception occured while reading svg file", e);
          }
        }
      }

    }

    @Order(20.0)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10.0)
      public class UserSvgField extends AbstractSvgField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UserSVG");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return SvgFieldForm.MainBox.ConfigurationBox.SvgSourceField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {

          getSvgSourceField().clearErrorStatus();

          try {
            getUserSvgField().setSvgDocument(parseDocument((String) newMasterValue));
          }
          catch (Exception e) {
            e.printStackTrace();
            getSvgSourceField().addErrorStatus(e.getMessage());
          }
        }

        private SVGDocument liveUpdateCircle(SVGDocument doc, String id) {
          SVGCircleElement circle = (SVGCircleElement) doc.getElementById(id);
          SVGLength x = (SVGLength) circle.getCx().getBaseVal();
          x.setValue((x.getValue() + 40) % 400);
          return doc;
        }

        @Override
        protected void execAppLinkAction(String ref) throws ProcessingException {
          if ("circle2".equals(ref)) {
            SVGDocument doc = (SVGDocument) getSvgDocument().cloneNode(true);
            setSvgDocument(liveUpdateCircle(doc, "circle2"));
          }
          else {
            MessageBox.createOk().header(TEXTS.get("SVGLink")).body(TEXTS.get("SVGLinkMessage")).body(ref).start();
          }
        }
      }

      @Order(20.0)
      public class SvgSourceField extends AbstractStringField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SvgDocument");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 200000;
        }

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
        }
      }
    }

    @Order(30.0)
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getSvgSourceField().setValue(TEXTS.get("SvgUserContent"));
      }
    }

    @Order(40.0)
    public class CloseButton extends AbstractCloseButton {
    }

    private SVGDocument loadDocument(String resourceName) throws IOException, ProcessingException {
      if (StringUtility.isNullOrEmpty(resourceName)) {
        return null;
      }
      return SVGUtility.readSVGDocument(ResourceBase.class.getResourceAsStream(resourceName));
    }

    private SVGDocument parseDocument(String svgData) throws IOException, ProcessingException {
      if (StringUtility.isNullOrEmpty(svgData)) {
        return null;
      }
      return SVGUtility.readSVGDocument(new ByteArrayInputStream(svgData.getBytes("UTF-8")));
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
