package org.ispw.calculator.logic;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

// bug canc e punto
public class PrimaryController {
	State state = State.INIT;
	StateNumberButton stateNum = StateNumberButton.OVERWRITE;
	State writing = State.INIT;
	Double firstNum;
	Double secondNum;
	boolean dotSet = false;

	@FXML
	JFXTextField displayTextField;

	@FXML
	private void sqrt() {
		System.out.println(state);
		if (state == State.NOOP) {
			firstNum = Double.valueOf(displayTextField.getText());
			double result = Math.sqrt(Double.valueOf(firstNum.toString()));
			displayResult(result);
			writing = State.INIT;
			stateNum = StateNumberButton.OVERWRITE;
		}
	}

	@FXML
	private void multiply() {
		if (state != State.INIT) {
			if (state != State.NOOP)
				equals();
			state = State.MULTIPLY;
			writing = State.INIT;
			stateNum = StateNumberButton.OVERWRITE;
			firstNum = Double.valueOf(displayTextField.getText());
		}
	}

	@FXML
	private void divide() {
		if (state != State.INIT) {
			if (state != State.NOOP)
				equals();
			state = State.DIVIDE;
			writing = State.INIT;
			stateNum = StateNumberButton.OVERWRITE;
			firstNum = Double.valueOf(displayTextField.getText());
		}
	}

	@FXML
	private void sum() {
		if (state != State.INIT) {
			if (state != State.NOOP)
				equals();
			state = State.ADD;
			writing = State.INIT;
			stateNum = StateNumberButton.OVERWRITE;
			firstNum = Double.valueOf(displayTextField.getText());
		}
	}

	@FXML
	private void subtract() {
		if (state != State.INIT) {
			if (state != State.NOOP)
				equals();
			state = State.SUBTRACT;
			writing = State.INIT;
			stateNum = StateNumberButton.OVERWRITE;
			firstNum = Double.valueOf(displayTextField.getText());
		}
	}

	@FXML
	private void dot() {
		if (stateNum == StateNumberButton.OVERWRITE) {
			displayTextField.setText("0.");
			stateNum = StateNumberButton.APPEND;
		} else if (state != State.INIT) {
			dotSet = displayTextField.getText().contains(".");
			if (!dotSet) {
				dotSet = true;
				displayTextField.appendText(".");
			}
		}
	}

	@FXML
	private void equals() {
		System.out.println(state);
		if (state != State.INIT) {
			secondNum = Double.valueOf(displayTextField.getText());
			Double result;
			switch (state) {
			case ADD:
				result = firstNum + secondNum;
				break;
			case SUBTRACT:
				result = firstNum - secondNum;
				break;
			case MULTIPLY:
				result = firstNum * secondNum;
				break;
			case DIVIDE:
				result = firstNum / secondNum;
				break;
			default:
				return;
			}
			System.out.println(firstNum + " " + state + " " + secondNum + " = " + result);
			state = State.NOOP;
			writing = State.INIT;
			stateNum = StateNumberButton.OVERWRITE;
			displayResult(result);
		}
	}

	@FXML
	private void pressedNumber(ActionEvent event) {
		if (stateNum == StateNumberButton.OVERWRITE)
			displayTextField.clear();
		if (state == State.INIT)
			state = State.NOOP;
		JFXButton numButton = (JFXButton) event.getSource();
		if (numButton.getId().equals("num")) {
			writing = State.INSERTING;
			stateNum = StateNumberButton.APPEND;
			displayTextField.appendText(numButton.getText());
		}
	}

	@FXML
	private void delete() {
		System.out.println(state);
		if (state != State.INIT) {
			if (displayTextField.getText().length() <= 1) {
				displayTextField.setText("0.0");
				stateNum = StateNumberButton.OVERWRITE;
			} else if (stateNum == StateNumberButton.OVERWRITE) {
				displayTextField.setText("0.0");
			} else {
				String text = displayTextField.getText();
				displayTextField.setText(text.substring(0, text.length() - 1));
			}
		}
	}

	@FXML
	private void clear() {
		state = State.INIT;
		displayTextField.setText("0.0");
		stateNum = StateNumberButton.OVERWRITE;
		writing = State.INIT;
	}

	@FXML
	private void operation() {

	}

	private void displayResult(Double result) {
		if (result.isNaN()) {
			displayTextField.setText("Invalid operation");
			state = State.INIT;
		} else {

			displayTextField.setText(String.valueOf(result.floatValue()));
		}
	}
}
