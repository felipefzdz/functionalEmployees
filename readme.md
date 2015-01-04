**EMPLOYEES SERVICE**

This project showcases Java 8 functional capabilities for manipulating collections and parsing files. 

The application has a really small domain that models employees. There are two kinds of employees: seniors and interns. Every intern has to have a mentor that takes care of him, and every senior can have 0 to N interns.

The data cames from text files living on the classpath. There are two data formats. First one just recognizes one type of employee, and it was used in the first versions of the app. Second one includes senior/intern roles and refines some of the other data. The app should be able to parse both formats as it's expected to receive some legacy data input.

We could try to understand this app looking at its two main modules: parser and reporter. The parser takes some csv input and extracts domain object using functional constructs and trying to be as general as possible. The reporter try to answer a couple of business questions about that data.