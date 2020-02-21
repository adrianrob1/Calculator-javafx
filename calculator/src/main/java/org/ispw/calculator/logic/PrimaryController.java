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
		firstNum = Double.valueOf(displayTextField.getText());
		displayTextField.setText(String.valueOf(Math.sqrt(firstNum)));
		state = State.NOOP;
		dotSet = true;
	}

	@FXML
	private void multiply() {
		dotSet = false;
		state = State.MULTIPLY;
		stateNum = StateNumberButton.OVERWRITE;
		firstNum = Double.valueOf(displayTextField.getText());
		displayTextField.setText("0.0");
	}

	@FXML
	private void divide() {
		dotSet = false;
		state = State.DIVIDE;
		stateNum = StateNumberButton.OVERWRITE;
		firstNum = Double.valueOf(displayTextField.getText());
		displayTextField.setText("0.0");
	}

	@FXML
	private void sum() {
		dotSet = false;
		state = State.ADD;
		stateNum = StateNumberButton.OVERWRITE;
		firstNum = Double.valueOf(displayTextField.getText());
		displayTextField.setText("0.0");
	}

	@FXML
	private void subtract() {
		state = State.SUBTRACT;
		stateNum = StateNumberButton.OVERWRITE;
		firstNum = Double.valueOf(displayTextField.getText());
		displayTextField.setText("0.0");
	}

	@FXML
	private void dot() {
		if (displayTextField.getText().equals("0.0") || displayTextField.getText().equals("Invalid!")) {
			displayTextField.setText("0.");
			stateNum = StateNumberButton.APPEND;
		} else if (!dotSet) {
			displayTextField.appendText(".");
			stateNum = StateNumberButton.APPEND;
		}
		dotSet = true;
	}

	@FXML
	private void equals() {
		if (displayTextField.getText().isEmpty() || firstNum == null)
			return;
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
			result = Double.valueOf(0);
		}
		System.getLogger("calculator").log(System.Logger.Level.INFO,
				firstNum + "  " + state + "  " + secondNum + "  = " + result);
		dotSet = result.toString().contains(".");
		state = State.NOOP;
		stateNum = StateNumberButton.OVERWRITE;
		if (result.isNaN()) {
			displayTextField.setText("Invalid!");
			writing = State.INIT;
			return;
		}
		displayTextField.setText(String.valueOf(result));
		writing = State.INIT;
	}

	@FXML
	private void pressedNumber(ActionEvent event) {
		JFXButton numButton = (JFXButton) event.getSource();
		if (stateNum == StateNumberButton.OVERWRITE
				&& (displayTextField.getText().isEmpty() || Double.valueOf(displayTextField.getText()) == 0.0)) {
			displayTextField.setText(numButton.getText());
			stateNum = StateNumberButton.APPEND;
			writing = State.INSERTING;
		} else {
			displayTextField.appendText(numButton.getText());
			writing = State.INSERTING;
		}
	}

	@FXML
	private void delete() {
		if(writing == State.INIT) {
			displayTextField.setText("0.0");
			return;
		}
		if (displayTextField.getText().equals("Invalid!") || displayTextField.getText().equals("Infinite") 
				|| displayTextField.getLength() == 1 || displayTextField.getText().equals("0.0")) {
			displayTextField.setText("0.0");
			return;
		}
		if (displayTextField.getText().charAt(displayTextField.getLength() - 1) == '.')
			dotSet = false;
		displayTextField.deleteText(displayTextField.getText().length() - 1, displayTextField.getText().length());
	}

	@FXML
	private void clear() {
		displayTextField.setText("0.0");
		firstNum = null;
		secondNum = null;
		dotSet = false;
		state = State.INIT;
		stateNum = StateNumberButton.OVERWRITE;
	}
}
