package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import sm.ContractorSM;

import static model.ClientsState.NO_CONNECTION;
import static model.ContractorEvent.INCOME_INVITATION;
import static model.ContractorEvent.RC_ERROR;

@SpringBootApplication
@ComponentScan(value = "sm")
public class Application implements CommandLineRunner {
    @Autowired
    ContractorSM contractorSM;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        contractorSM.executeSM(NO_CONNECTION, INCOME_INVITATION);
        System.out.println("Next step");

        contractorSM.executeSM(NO_CONNECTION, RC_ERROR);
    }
}