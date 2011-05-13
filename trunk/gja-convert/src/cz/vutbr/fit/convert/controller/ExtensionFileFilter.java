/**
 * Convert
 * Program pro prevod skladeb do formatu ogg a flac
 * Projekt do GJA na FIT VUT 2010/2011
 *
 * Autori:
 * Tomas Izak <izakt00@stud.fit.vutbr.cz>
 * Pavel Pokorny <xpokor12@stud.fit.vutbr.cz>
 */
package cz.vutbr.fit.convert.controller;

import java.io.File;
import javax.swing.filechooser.FileFilter;


/**
 * Trida pro pouziti file filtru
 * Filtruje soubory, ktere je mozne vybrat v dialogu vyberu souboru.
 * 
 */
class ExtensionFileFilter extends FileFilter{
  String description;
  String extensions[];

  public ExtensionFileFilter(String description, String extension) {
    this(description, new String[] { extension });
  }

  public ExtensionFileFilter(String description, String extensions[]) {
    if (description == null) {
      this.description = extensions[0];
    } else {
      this.description = description;
    }
    this.extensions = (String[]) extensions.clone();
    toLower(this.extensions);
  }

  private void toLower(String array[]) {
    for (int i = 0, n = array.length; i < n; i++) {
      array[i] = array[i].toLowerCase();
    }
  }

  public String getDescription() {
    return description;
  }

  public boolean accept(File file) {
    if (file.isDirectory()) {
      return true;
    } else {
      String path = file.getAbsolutePath().toLowerCase();
      for (int i = 0, n = extensions.length; i < n; i++) {
        String extension = extensions[i];
        if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
          return true;
        }
      }
    }
    return false;
  }
}