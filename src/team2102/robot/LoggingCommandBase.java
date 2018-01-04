package team2102.robot;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.command.Command;

// TODO: Another one for command groups
public class LoggingCommandBase extends Command {

	protected final Logger LOGGER = Logger.getLogger(getClass().getName());

	@Override
	protected void initialize() {
		LOGGER.logp(Level.INFO, getClass().getName(), "initialize", getClass().getSimpleName());
	}

	@Override
	protected void end() {
		LOGGER.logp(Level.INFO, getClass().getName(), "end", getClass().getSimpleName());
	}

	@Override
	protected void interrupted() {
		LOGGER.logp(Level.INFO, getClass().getName(), "interrupted", getClass().getSimpleName());
	}

	@Override
	protected boolean isFinished() {
		// Makes InstantCommand-like commands easier
		return true;
	}

}
