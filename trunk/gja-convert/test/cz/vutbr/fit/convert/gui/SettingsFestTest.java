/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.convert.gui;


//Requires Fest library:
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.junit.BeforeClass;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import cz.vutbr.fit.convert.settings.Config;
import cz.vutbr.fit.convert.settings.Lang;

//Requires JUnit 4.1 library:
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JSpinnerFixture;
import org.fest.swing.fixture.JTabbedPaneFixture;
import org.junit.Test;
import org.junit.After;  
import org.junit.Before;
import static org.junit.Assert.*;

/**
 *
 * @author ppo
 */

public class SettingsFestTest {

    private FrameFixture window;
    private DialogFixture settings_dialog;
    private JTabbedPaneFixture settings_tabbed_pane;

    @Before
    public void setUp() {
        Config.init();
        Lang.init();

        // nastaveni ogg samplerate na 44100
        Config.set("ogg_samplingrate", "44100");
        
        MainWindow dialog = GuiActionRunner.execute(new GuiQuery<MainWindow>() {
            protected MainWindow executeInEDT() {
                return new MainWindow();
            }
        });
        // zobrazeni aplikace
        window = new FrameFixture(dialog);
        window.show();
        
        // zobrazeni nastaveni
        window.menuItem("settings_window_menuitem").click();
        settings_dialog = window.dialog("settings_window_dialog");

        settings_tabbed_pane = settings_dialog.tabbedPane("settings_tabbed_pane");
    }
    
    @BeforeClass
    public static void setUpOnce() {
      FailOnThreadViolationRepaintManager.install();
    }

    /**
     * Otestuje, zda se uklada nastaveni poctu max vlaken.
     */
    @Test
    public void shouldSaveMaxTasksSettings() {
        
        JSpinnerFixture maxtask = settings_dialog.spinner("max_tasks_spinner");
        Integer old_maxtask_val = Integer.decode(Config.get("MaxTasks"));
        maxtask.requireValue(old_maxtask_val);
        maxtask.increment(3);
        maxtask.requireValue(old_maxtask_val+3);
        
        settings_dialog.button("okButton").click();
        assertEquals(old_maxtask_val+3, Integer.decode(Config.get("MaxTasks")).intValue() );

        window.menuItem("settings_window_menuitem").click();

        maxtask = settings_dialog.spinner("max_tasks_spinner");
        old_maxtask_val = Integer.decode(Config.get("MaxTasks"));
        maxtask.requireValue(old_maxtask_val);
        maxtask.decrement(3);
        maxtask.requireValue(old_maxtask_val-3);

        settings_dialog.button("okButton").click();
        assertEquals(old_maxtask_val-3, Integer.decode(Config.get("MaxTasks")).intValue() );
    }

    @Test
    public void shouldSaveOggSettings() {

        settings_tabbed_pane.selectTab(Lang.get("ogg_format"));

        JSpinnerFixture samplerate = settings_dialog.spinner("ogg_samplerate_spinner");
        JSpinnerFixture quality = settings_dialog.spinner("ogg_quality_spinner");

        Integer old_samplerate_val = Integer.decode(Config.get("ogg_samplingrate"));
        Integer old_quality_val = Integer.decode(Config.get("ogg_quality"));

        samplerate.requireValue(old_samplerate_val.toString());
        quality.requireValue(old_quality_val);

        samplerate.decrement(2);
        samplerate.requireValue("24000");

        quality.increment(100);
        quality.requireValue(old_quality_val+100);

        settings_dialog.button("okButton").click();

        assertEquals(old_quality_val+100, Integer.decode(Config.get("ogg_quality")).intValue() );
        assertEquals(24000, Integer.decode(Config.get("ogg_samplingrate")).intValue());

        window.menuItem("settings_window_menuitem").click();

        settings_tabbed_pane.selectTab(Lang.get("ogg_format"));

        samplerate = settings_dialog.spinner("ogg_samplerate_spinner");
        quality = settings_dialog.spinner("ogg_quality_spinner");

        samplerate.requireValue("24000");
        quality.requireValue(old_quality_val+100);

        samplerate.increment(2);
        samplerate.requireValue("44100");

        quality.decrement(100);
        quality.requireValue(old_quality_val);
        
        settings_dialog.button("okButton").click();

        assertEquals(old_quality_val.intValue(), Integer.decode(Config.get("ogg_quality")).intValue());
        assertEquals(44100, Integer.decode(Config.get("ogg_samplingrate")).intValue());
    }
        
    @After
    public void tearDown() {
        window.cleanUp();
    }

}