package domain;

import data.DataAccessException;
import data.PanelRepoDouble;
import models.Panel;
import models.PanelMaterial;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PanelServiceTest {
    PanelService service = new PanelService(new PanelRepoDouble());

    // Input validation tests
////////////////////////////////////////   ADD ////////////////////////////////////////////////
    @Test
    void shouldAddPanel() throws DataAccessException {
        Panel testOne = new Panel();
        testOne.setId(1);
        testOne.setRow(5);
        testOne.setColumn(9);
        testOne.setSection("West");
        testOne.setTracking(true);
        testOne.setMaterial(PanelMaterial.CD_TE);
        testOne.setYearInstalled(2011);

        PanelResult result = service.add(testOne);

        assertTrue(result.isSuccess());
    }
    // Null Panel
    @Test
    void shouldNotAddNullPanel() throws DataAccessException {
        PanelResult result = service.add(null);
        assertFalse(result.isSuccess());
    }

    // Null Section

    @Test
    void shouldNotAddNullSection() throws DataAccessException {
        Panel testOne = new Panel();
        testOne.setId(1);
        testOne.setRow(55);
        testOne.setColumn(9);
        testOne.setSection(null);
        testOne.setTracking(true);
        testOne.setMaterial(PanelMaterial.CD_TE);
        testOne.setYearInstalled(2011);

        PanelResult result = service.add(testOne);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotAddBlankSection() throws DataAccessException {
        Panel testOne = new Panel();
        testOne.setId(1);
        testOne.setRow(-5);
        testOne.setColumn(9);
        testOne.setSection("");
        testOne.setTracking(true);
        testOne.setMaterial(PanelMaterial.CD_TE);
        testOne.setYearInstalled(2011);

        PanelResult result = service.add(testOne);

        assertFalse(result.isSuccess());
        assertEquals(2, result.getMessages().size());
    }

    //Row and Column between 0 and 250

    @Test
    void shouldNotAddInvalidRow() throws DataAccessException {
        Panel testOne = new Panel();
        testOne.setId(1);
        testOne.setRow(-5);
        testOne.setColumn(9);
        testOne.setSection("West");
        testOne.setTracking(true);
        testOne.setMaterial(PanelMaterial.CD_TE);
        testOne.setYearInstalled(2011);

        PanelResult result = service.add(testOne);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("Row"));
    }

    @Test
    void shouldNotAddInvalidColumn() throws DataAccessException {
        Panel testOne = new Panel();
        testOne.setId(1);
        testOne.setRow(5);
        testOne.setColumn(330);
        testOne.setSection("West");
        testOne.setTracking(true);
        testOne.setMaterial(PanelMaterial.CD_TE);
        testOne.setYearInstalled(2011);

        PanelResult result = service.add(testOne);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("Column"));

    }

    //Material is required

    @Test
    void shouldNotAddNullMaterial() throws DataAccessException {
        Panel testOne = new Panel();
        testOne.setId(1);
        testOne.setRow(5);
        testOne.setColumn(9);
        testOne.setSection("West");
        testOne.setTracking(true);
        testOne.setMaterial(null);
        testOne.setYearInstalled(2011);

        PanelResult result = service.add(testOne);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("Material"));
    }
    // Year should be in the past but not before 1980
    @Test
    void yearShouldBePast() throws DataAccessException {
        Date today = new Date();
        int year = today.getYear() + 1900;
        Panel testOne = new Panel();
        testOne.setId(1);
        testOne.setRow(5);
        testOne.setColumn(9);
        testOne.setSection("West");
        testOne.setTracking(true);
        testOne.setMaterial(null);
        testOne.setYearInstalled(year);

        PanelResult result = service.add(testOne);

        assertFalse(result.isSuccess());
    }
    @Test
    void yearShouldBeAfterEighty() throws DataAccessException {
        Panel testOne = new Panel();
        testOne.setId(1);
        testOne.setRow(5);
        testOne.setColumn(9);
        testOne.setSection("West");
        testOne.setTracking(true);
        testOne.setMaterial(null);
        testOne.setYearInstalled(1979);

        PanelResult result = service.add(testOne);

        assertFalse(result.isSuccess());
    }
    //No duplicates
    @Test
    void shouldNotCreatDuplicate() throws DataAccessException {
        Panel testOne = new Panel();
        testOne.setId(1);
        testOne.setRow(5);
        testOne.setColumn(9);
        testOne.setSection("West");
        testOne.setTracking(true);
        testOne.setMaterial(PanelMaterial.CD_TE);
        testOne.setYearInstalled(2011);
        Panel testTwo = new Panel();

        testTwo.setId(2);
        testTwo.setRow(5);
        testTwo.setColumn(9);
        testTwo.setSection("West");
        testTwo.setTracking(true);
        testTwo.setMaterial(PanelMaterial.CD_TE);
        testTwo.setYearInstalled(2011);

        // should add the first test, should not add the section
        PanelResult result = service.add(testOne);
        PanelResult resultTwo = service.add(testTwo);

        assertTrue(result.isSuccess());
        assertFalse(resultTwo.isSuccess());
        assertEquals(1, resultTwo.getMessages().size());
        assertTrue(resultTwo.getMessages().get(0).contains("Duplicate"));
    }

    ////////////////////////////////////////   UPDATE  ////////////////////////////////////////////////


    ////////////////////////////////////////   DELETE  ////////////////////////////////////////////////
}