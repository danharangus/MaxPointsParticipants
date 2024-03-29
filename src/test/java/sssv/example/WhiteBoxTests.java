package sssv.example;

import domain.Tema;
import junit.framework.TestCase;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.NotaFileRepository;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

public class WhiteBoxTests extends TestCase {
    private TemaValidator temaValidator;
    private TemaXMLRepo temaFileRepository;



    private Service service;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public WhiteBoxTests( String testName )
    {
        super( testName );
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        temaValidator = new TemaValidator();
        temaValidator = new TemaValidator();

        service = new Service(null,
                null,
                temaFileRepository,
                temaValidator,
                null,
                null); // Adjust constructor as necessary
    }

    /**
     * Add assignment with invalid number throws ValidationException
     */
    public void testAddAssignmentInvalidNumber() {
        Tema assignment = new Tema("", "description", 1, 2);
        try {
            service.addTema(assignment);
            fail("ValidationException not thrown");
        } catch (ValidationException e) {
            assertEquals("Numar tema invalid!", e.getMessage());
        }
    }

    /**
     * Add assignment with invalid description throws ValidationException
     */
    public void testAddAssignmentInvalidDescription() {
        Tema assignment = new Tema("1", "", 1, 2);
        try {
            service.addTema(assignment);
            fail("ValidationException not thrown");
        } catch (ValidationException e) {
            assertEquals("Descriere invalida!", e.getMessage());
        }
    }
}
