// Copyright 2000-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.navigation;

import com.intellij.openapi.editor.markup.TextAttributes;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Represents presentation in target popup as follows:<br/>
 * <code>| $icon $presentable_text (in $location_text) spacer $right_text $right_icon |</code><br/>
 * Elements before spacer are aligned to the left, right text and right icon are aligned to the right.
 */
@Experimental
public interface TargetPopupPresentation {

  default @Nullable Color getBackgroundColor() {
    return null;
  }

  default @Nullable Icon getIcon() {
    return null;
  }

  @Nls @NotNull String getPresentableText();

  /**
   * @return attributes to highlight {@link #getPresentableText()}
   */
  default @Nullable TextAttributes getPresentableAttributes() {
    return null;
  }

  default @Nls @Nullable String getLocationText() {
    return null;
  }

  /**
   * @return attributes to highlight {@link #getLocationText()}
   */
  default @Nullable TextAttributes getLocationAttributes() {
    return null;
  }

  default @Nls @Nullable String getRightText() {
    return null;
  }

  default @Nullable Icon getRightIcon() {
    return null;
  }
}
