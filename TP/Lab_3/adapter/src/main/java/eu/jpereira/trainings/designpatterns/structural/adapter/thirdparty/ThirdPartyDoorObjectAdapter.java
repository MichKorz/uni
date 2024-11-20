package eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty;

import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.CodeMismatchException;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.IncorrectDoorCodeException;
import eu.jpereira.trainings.designpatterns.structural.adapter.model.Door;

public class ThirdPartyDoorObjectAdapter implements Door
{
    private boolean open = false;

    private String code = ThirdPartyDoor.DEFAULT_CODE;

    @Override
    public void open(String code) throws IncorrectDoorCodeException {
        if (code.equals(this.code)) {
            this.open = true;
        }
        else throw new IncorrectDoorCodeException();
    }

    @Override
    public void close() {
        this.open = false;
    }

    @Override
    public boolean isOpen() {
        return this.open;
    }

    @Override
    public void changeCode(String oldCode, String newCode, String newCodeConfirmation) throws IncorrectDoorCodeException, CodeMismatchException {
        if(oldCode.equals(this.code) && newCode.equals(newCodeConfirmation)) {
            this.code = newCode;
        }
        else if(!oldCode.equals(this.code)){
            throw new IncorrectDoorCodeException();
        }
        else throw new CodeMismatchException();
    }

    @Override
    public boolean testCode(String code) {
        return code.equals(this.code);
    }
}
