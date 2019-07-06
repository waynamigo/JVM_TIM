package com.ignoreos.jvmqq.Theme;
import com.googlecode.lanterna.graphics.*;
import com.googlecode.lanterna.TextColor;

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

