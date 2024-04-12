package sssv.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import junit.framework.Assert;
import junit.framework.TestCase;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.time.LocalDate;
import static org.mockito.Mockito.*;

public class BBITests extends TestCase {
    @Mock
    private StudentValidator studentValidator;

    @Mock
    private TemaValidator temaValidator;

    @Mock
    private NotaValidator notaValidator;

    @Mock
    private StudentXMLRepo studentXMLRepository;

    @Mock
    private TemaXMLRepo temaXMLRepository;

    @Mock
    private NotaXMLRepo notaXMLRepository;

    private Service service;

    @BeforeEach
    public void setup() {
        studentValidator = mock(StudentValidator.class);
        temaValidator = mock(TemaValidator.class);
        notaValidator = mock(NotaValidator.class);
        studentXMLRepository = mock(StudentXMLRepo.class);
        temaXMLRepository = mock(TemaXMLRepo.class);
        notaXMLRepository = mock(NotaXMLRepo.class);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @AfterEach
    public void tearDown() {
        studentValidator = null;
        temaValidator = null;
        notaValidator = null;
        studentXMLRepository = null;
        temaXMLRepository = null;
        notaXMLRepository = null;
        service = null;
    }

    @Test
    public void addStudent_Valid_StudentIsAdded(){
        Student testStudent = new Student("1", "Test Student", 933, "test_email@email.ro");
        try {
            service.addStudent(testStudent);
        } catch (Exception e) {
            Assertions.fail();
        }
        service.deleteStudent("1");
    }

    @Test
    public void addNota_Valid_NotaIsAdded() {
        Nota testNota = new Nota("1", "1", "1", 10, LocalDate.of(2024, 4, 11));
        try {
            when(studentXMLRepository.findOne("1")).thenReturn(new Student("1", "Test Student", 933, "test"));
            when(temaXMLRepository.findOne("1")).thenReturn(new Tema("1", "Test Tema", 6, 10));
            service.addNota(testNota, "feedback");
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
        service.deleteNota("1");
    }

    @Test
    public void addTema_Valid_TemaIsAdded() {
        Tema testTema = new Tema("1", "Test Tema", 1, 10);
        try {
            service.addTema(testTema);
        } catch (Exception e) {
            Assertions.fail();
        }
        service.deleteTema("1");
    }

    @Test
    public void addStudent_Valid_addTema_Valid_addNota_Valid_ThrowsException() {
        String studentId = "1";
        String nume = "Dan";
        int grupa = 933;
        String email = "dan@yahoo.com";
        Student student = new Student(studentId, nume, grupa, email);

        String nrTema = "1";
        String descriere = "descriere";
        int deadline = 6;
        int primire = 2;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        String notaId = "nt1";
        double notaVal = 9.5;
        LocalDate date = LocalDate.of(2021, 10, 10);
        Nota nota = new Nota(notaId, studentId, nrTema, notaVal, date);

        try {
            doNothing().when(studentValidator).validate(student);
            when(studentXMLRepository.save(student)).thenReturn(null);

            doNothing().when(temaValidator).validate(tema);
            when(temaXMLRepository.save(tema)).thenReturn(null);

            when(studentXMLRepository.findOne(studentId)).thenReturn(student);
            when(temaXMLRepository.findOne(nrTema)).thenReturn(tema);

            Student returnedStudent = service.addStudent(student);
            Assertions.assertNull(returnedStudent);

            Tema returnedTema = service.addTema(tema);
            Assertions.assertNull(returnedTema);

            Assertions.assertThrows(ValidationException.class, () -> service.addNota(nota, "feedback"));
        } catch (ValidationException ve) {
            ve.printStackTrace();
        }
    }
}