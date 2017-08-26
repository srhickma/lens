package com.konjex.lens.commands;


import com.konjex.lens.commands.exceptions.CommandNotFoundException;
import com.konjex.lens.commands.exceptions.InvalidCommandNameException;
import com.konjex.util.DoublyLinkedList;
import com.konjex.util.LinkedListNode;
import org.apache.log4j.Logger;

/**
 * Class for running lens commands.
 */
public class CommandRunner {

    private static Logger log = Logger.getLogger(CommandRunner.class);

    private static final int MAX_HISTORY_SIZE = 64;
    private DoublyLinkedList<String> history = new DoublyLinkedList<>();
    private LinkedListNode<String> currentCommand;

    public CommandRunner(){
        history.addFirst("");
        currentCommand = history.getFirst();
    }

    public void run(String inputText){
        try{
            String commandName = CommandSanitizer.sanitize(inputText);
            Command command = CommandProvider.get(commandName);
            log.info("Executing command " + command.getName());
            command.run();

            history.getFirst().setValue(inputText);
            history.addFirst("");
            if(history.size() > MAX_HISTORY_SIZE){
                history.removeLast();
            }
        }
        catch(InvalidCommandNameException | CommandNotFoundException e){
            log.error(e);
        }
    }

    public String shiftToFirst(){
        currentCommand = history.getFirst();
        return history.getFirst().getValue();
    }

    public String shiftDown(){
        if(currentCommand.hasPrev()){
            currentCommand = currentCommand.getPrev();
        }
        return currentCommand.getValue();
    }

    public String shiftUp(){
        if(currentCommand.hasNext()){
            currentCommand = currentCommand.getNext();
        }
        return currentCommand.getValue();
    }

}
