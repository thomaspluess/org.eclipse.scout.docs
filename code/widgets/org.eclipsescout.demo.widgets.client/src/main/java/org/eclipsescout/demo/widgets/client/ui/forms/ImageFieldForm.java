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

import java.net.URL;
import java.util.List;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.dnd.ResourceListTransferObject;
import org.eclipse.scout.commons.dnd.TransferObject;
import org.eclipse.scout.commons.resource.BinaryResource;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ResourceBase;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ConfigurationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ConfigurationBox.Image1Field;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ConfigurationBox.Image2Field;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ConfigurationBox.ImageURLField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.AlignedCenterField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.AlignedRightField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.ExamplesBox.IconContentField;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm.MainBox.SampleContentButton;
import org.eclipsescout.demo.widgets.shared.Icons;

public class ImageFieldForm extends AbstractForm implements IPageForm {

  public ImageFieldForm() {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ImageField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public AlignedCenterField getAlignedCenterField() {
    return getFieldByClass(AlignedCenterField.class);
  }

  public AlignedRightField getAlignedRightField() {
    return getFieldByClass(AlignedRightField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public IconContentField getIconContentField() {
    return getFieldByClass(IconContentField.class);
  }

  public Image1Field getImage1Field() {
    return getFieldByClass(Image1Field.class);
  }

  public Image2Field getImage2Field() {
    return getFieldByClass(Image2Field.class);
  }

  public ImageURLField getImageURLField() {
    return getFieldByClass(ImageURLField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public SampleContentButton getSampleContentButton() {
    return getFieldByClass(SampleContentButton.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    public static final String SCOUT_LOGO = "images/eclipse_scout_logo.png";
    public static final String SCOUT_LOGO_FILENAME = "eclipse_scout_logo.png";
    public static final String BIRD = "http://2.bp.blogspot.com/_LDF9z4ZzZHo/TQZI-CUPl2I/AAAAAAAAAfc/--DuSZRxywM/s1600/bird_1008.jpg";
    public static final String BIRD_OFFLINE = "images/bird_1008.jpg";

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      public class DefaultField extends AbstractImageField {

        @Override
        protected int getConfiguredDragType() {
          return TYPE_FILE_TRANSFER;
        }

        @Override
        protected int getConfiguredDropType() {
          return TYPE_FILE_TRANSFER;
        }

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return TEXTS.get("ImageDragDropSupport");
        }

        @Override
        protected TransferObject execDragRequest() {
          Object content = getImage();

          if (content instanceof byte[]) {
            BinaryResource resource = new BinaryResource(getImageId(), (byte[]) content);
            return new ResourceListTransferObject(resource);
          }

          return null;
        }

        @Override
        protected void execDropRequest(TransferObject transferObject) {
          clearErrorStatus();

          if (transferObject instanceof ResourceListTransferObject) {
            List<BinaryResource> resources = ((ResourceListTransferObject) transferObject).getResources();

            if (resources.size() > 0) {
              BinaryResource resource = CollectionUtility.firstElement(resources);
              // if you want to work with buffered images
              // BufferedImage bi = ImageIO.read(new FileInputStream(fileName[0]));
              // setImage(bi);
              setImage(resource.getContent());
              setImageId(resource.getFilename());
            }
          }
        }

        @Override
        protected void execInitField() {
          clearErrorStatus();

          try {
            setImage(IOUtility.getContent(ResourceBase.class.getResourceAsStream(SCOUT_LOGO)));
            setImageId(SCOUT_LOGO_FILENAME);
          }
          catch (Exception e) {
            e.printStackTrace();
            addErrorStatus(e.getMessage());
          }
        }
      }

      @Order(20)
      public class IconContentField extends AbstractImageField {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return -1;
        }

        @Override
        protected String getConfiguredImageId() {
          return Icons.EclipseScout;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AlignedLeft");
        }
      }

      @Order(30)
      public class AlignedCenterField extends AbstractImageField {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 0;
        }

        @Override
        protected String getConfiguredImageId() {
          return Icons.EclipseScout;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AlignedCenter");
        }
      }

      @Order(40)
      public class AlignedRightField extends AbstractImageField {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 1;
        }

        @Override
        protected String getConfiguredImageId() {
          return Icons.EclipseScout;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AlignedRight");
        }
      }
    }

    @Order(20)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      private URL getUrl(String urlString) throws Exception {
        if (urlString.equals(BIRD)) {
          return ResourceBase.class.getResource(BIRD_OFFLINE);
        }
        return new URL((String) urlString);
      }

      @Order(10)
      public class Image1Field extends AbstractImageField {

        @Override
        protected boolean getConfiguredAutoFit() {
          return true;
        }

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return "View 1";
        }

        @Override
        protected String getConfiguredTooltipText() {
          return "Image alignment: horizontally and vertically centered.";
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return ImageFieldForm.MainBox.ConfigurationBox.ImageURLField.class;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) {
          getImageURLField().clearErrorStatus();
          try {
            URL url = getUrl((String) newMasterValue);
            setImage(IOUtility.getContent(url.openStream()));
          }
          catch (Exception e) {
            e.printStackTrace();
            getImageURLField().addErrorStatus(e.getMessage());
          }
        }
      }

      @Order(15)
      public class Image1ScrollbarsEnabledCheckbox extends AbstractCheckBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ScrollbarEnabled");
        }

        @Override
        protected void execChangedValue() {
          getImage1Field().setScrollBarEnabled(getValue());
        }

        @Override
        protected void execInitField() {
          setValueChangeTriggerEnabled(false);
          try {
            setValue(getImage1Field().isScrollBarEnabled());
          }
          finally {
            setValueChangeTriggerEnabled(true);
          }
        }
      }

      @Order(16)
      public class Image1AutoFitCheckbox extends AbstractCheckBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AutoFit");
        }

        @Override
        protected void execChangedValue() {
          getImage1Field().setAutoFit(getValue());
        }

        @Override
        protected void execInitField() {
          setValueChangeTriggerEnabled(false);
          try {
            setValue(getImage1Field().isAutoFit());
          }
          finally {
            setValueChangeTriggerEnabled(true);
          }
        }
      }

      @Order(20)
      public class Image2Field extends AbstractImageField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return "View 2";
        }

        @Override
        protected String getConfiguredTooltipText() {
          return "Image alignment: bottom right";
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return ImageFieldForm.MainBox.ConfigurationBox.ImageURLField.class;
        }

        @Override
        protected boolean getConfiguredScrollBarEnabled() {
          return true;
        }

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 1;
        }

        @Override
        protected int getConfiguredVerticalAlignment() {
          return 1;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) {
          getImageURLField().clearErrorStatus();
          try {
            URL url = getUrl((String) newMasterValue);
            setImage(IOUtility.getContent(url.openStream()));
          }
          catch (Exception e) {
            e.printStackTrace();
            getImageURLField().addErrorStatus(e.getMessage());
          }
        }
      }

      @Order(25)
      public class Image2ScrollbarEnabledFieldCheckbox extends AbstractCheckBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ScrollbarEnabled");
        }

        @Override
        protected void execChangedValue() {
          getImage2Field().setScrollBarEnabled(getValue());
        }

        @Override
        protected void execInitField() {
          setValueChangeTriggerEnabled(false);
          try {
            setValue(getImage2Field().isScrollBarEnabled());
          }
          finally {
            setValueChangeTriggerEnabled(true);
          }
        }
      }

      @Order(26)
      public class Image2AutoFitCheckbox extends AbstractCheckBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("AutoFit");
        }

        @Override
        protected void execChangedValue() {
          getImage2Field().setAutoFit(getValue());
        }

        @Override
        protected void execInitField() {
          setValueChangeTriggerEnabled(false);
          try {
            setValue(getImage2Field().isAutoFit());
          }
          finally {
            setValueChangeTriggerEnabled(true);
          }
        }
      }

      @Order(30)
      public class ImageURLField extends AbstractStringField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ImageURL");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }
      }
    }

    @Order(30)
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() {
        getImageURLField().setValue(BIRD);
      }
    }

    @Order(40)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
