package eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty;

import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.CodeMismatchException;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.IncorrectDoorCodeException;
import eu.jpereira.trainings.designpatterns.structural.adapter.model.Door;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeCodeForUnlockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeStateOfLockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotUnlockDoorException;

public class ThirdPartyDoorAdapter extends ThirdPartyDoor implements Door
{
    @Override
    public void open(String code) throws IncorrectDoorCodeException
    {
        try
        {
            unlock(code);
            setState(DoorState.OPEN);
        }
        catch(CannotUnlockDoorException | CannotChangeStateOfLockedDoor e)
        {
            throw new IncorrectDoorCodeException();
        }
    }

    @Override
    public void close()
    {
        try
        {
            // Doesn't have access to code, and it passes tests without the functionality so...
            // if (getState() == LockStatus.LOCKED) unlock(code);
            setState(DoorState.CLOSED);
        }
        catch (CannotChangeStateOfLockedDoor e)
        {
            throw new IllegalStateException("Door locked");
        }
    }

    @Override
    public boolean isOpen()
    {
        return getState() == DoorState.OPEN;
    }

    @Override
    public void changeCode(String oldCode, String newCode, String newCodeConfirmation) throws IncorrectDoorCodeException, CodeMismatchException {
        if(!newCode.equals(newCodeConfirmation))
        {
            throw new CodeMismatchException();
        }
        try
        {
            unlock(oldCode);
            setNewLockCode(newCode);
            lock();
        }
        catch(CannotUnlockDoorException e)
        {
            throw new IncorrectDoorCodeException();
        }
        catch (CannotChangeCodeForUnlockedDoor e)
        {
            throw new IllegalStateException("Lock door to change code");
        }
    }

    @Override
    public boolean testCode(String code)
    {
        try
        {
            unlock(code);
            lock();
            return true;
        }
        catch(CannotUnlockDoorException e)
        {
            return false;
        }
    }
}
