package com.konjex.lens.commands.types;

import com.konjex.lens.commands.Command;
import com.konjex.lens.commands.exceptions.FailedToRunCommandException;
import com.konjex.lens.commands.exceptions.InvalidCommandNameException;

/**
 * Command internal to lens.
 */
public class InternalCommand extends Command {

    private final Runnable action;

    public InternalCommand(String name, Runnable action) throws InvalidCommandNameException {
        super(name, CommandType.INTERNAL);
        this.action = action;
    }

    public void run() throws FailedToRunCommandException {
        try{
            action.run();
        }
        catch(Exception e){
            throw new FailedToRunCommandException(getName() , e);
        }
    }

}
