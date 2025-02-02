// Copyright 2000-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.ide.ui

import com.intellij.lang.LangBundle
import com.intellij.navigation.LocationPresentation.DEFAULT_LOCATION_PREFIX
import com.intellij.navigation.LocationPresentation.DEFAULT_LOCATION_SUFFIX
import com.intellij.navigation.TargetPopupPresentation
import com.intellij.ui.ColoredListCellRenderer
import com.intellij.ui.JBColor
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.SimpleTextAttributes.*
import com.intellij.ui.speedSearch.SearchAwareRenderer
import com.intellij.ui.speedSearch.SpeedSearchUtil.appendColoredFragmentForMatcher
import com.intellij.util.text.MatcherHolder
import com.intellij.util.ui.UIUtil
import org.jetbrains.annotations.ApiStatus.Experimental
import javax.swing.JList

@Experimental
abstract class TargetPresentationMainRenderer<T> : ColoredListCellRenderer<T>(), SearchAwareRenderer<T> {

  protected abstract fun getPresentation(value: T): TargetPopupPresentation?

  final override fun getItemSearchString(item: T): String? = getPresentation(item)?.presentableText

  final override fun customizeCellRenderer(list: JList<out T>, value: T, index: Int, selected: Boolean, hasFocus: Boolean) {
    val presentation = getPresentation(value) ?: run {
      append(LangBundle.message("target.presentation.invalid.target"), ERROR_ATTRIBUTES)
      return
    }
    val bgColor = presentation.backgroundColor ?: UIUtil.getListBackground()

    icon = presentation.icon
    background = if (selected) UIUtil.getListSelectionBackground(hasFocus) else bgColor

    val nameAttributes = presentation.presentableAttributes?.let(::fromTextAttributes)
                         ?: SimpleTextAttributes(STYLE_PLAIN, list.foreground)
    val matcher = MatcherHolder.getAssociatedMatcher(list)
    appendColoredFragmentForMatcher(presentation.presentableText, this, nameAttributes, matcher, bgColor, selected)

    presentation.locationText?.let { locationText ->
      val locationAttributes = presentation.locationAttributes?.let {
        merge(defaultLocationAttributes, fromTextAttributes(it))
      } ?: defaultLocationAttributes
      append(DEFAULT_LOCATION_PREFIX, defaultLocationAttributes)
      append(LangBundle.message("target.presentation.in.preposition") + " ", defaultLocationAttributes)
      append(locationText, locationAttributes)
      append(DEFAULT_LOCATION_SUFFIX, defaultLocationAttributes)
    }
  }

  companion object {
    private val defaultLocationAttributes = SimpleTextAttributes(STYLE_PLAIN, JBColor.GRAY)
  }
}
