package com.Tick_Tock.PCTIM.Theme;
import com.googlecode.lanterna.graphics.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.*;

public class MainTheme extends DelegatingThemeDefinition {
        
        public MainTheme(ThemeDefinition definition) {
            super(definition);
        }

    

        @Override
        public ThemeStyle getActive() {
            DefaultMutableThemeStyle mutableThemeStyle = new DefaultMutableThemeStyle(super.getActive());
            return mutableThemeStyle.setBackground(TextColor.Factory.fromString("MAGENTA"));
        }
    
}

