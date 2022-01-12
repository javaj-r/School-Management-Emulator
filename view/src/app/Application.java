package app;

import view.Console;

/**
 * @author javid
 * Created on 12/25/2021
 */
public class Application {

    public static final String help = """
            commands list:    
                
                    EXIT
                    LOGOUT
                    
                    CREATE-STUDENT    username password [Optional: firstname lastname nationalCode]
                    READ-STUDENT      (studentUsername or all)
                    UPDATE-STUDENT    studentUsername [password firstname lastname nationalCode] -> replace with [-] to not update
                    DELETE-STUDENT    studentUsername
                    SCORE-STUDENT     studentUsername courseName score
                    GET-STUDENT-I     (info or all)
                
                    CREATE-PROFESSOR  username password [Optional: firstname lastname nationalCode [Optional: yes]] -> yes for facultyMember
                    READ-PROFESSOR    (professorUsername or all)
                    UPDATE-PROFESSOR  professorUsername [password firstname lastname nationalCode yes] -> replace with [-] to not update
                    DELETE-PROFESSOR  professorUsername
                    
                    ADD-PROFESSOR-COURSE    professorUsername courseName termNumber
                    SALARY-PROFESSOR-I      termNumber
               
                    CREATE-EMPLOYEE   username password [Optional: firstname lastname nationalCode salary]
                    READ-EMPLOYEE     (employeeUsername or all)
                    UPDATE-EMPLOYEE   employeeUsername [password firstname lastname nationalCode salary] -> replace with [-] to not update
                    DELETE-EMPLOYEE   employeeUsername
                
                    CREATE-COURSE     courseName unit [Optional: requiredCourseName]
                    READ-COURSE       (courseName or all)
                    UPDATE-COURSE     courseName [unit requiredCourseName] -> replace with [-] to not update
                    DELETE-COURSE     courseName [requiredCourseName for deleting required course]
                    
                    REGISTER-COURSE   courseName
            """;



    public static void main(String[] args) {
        System.out.println(help);
        new Console();
    }


}
