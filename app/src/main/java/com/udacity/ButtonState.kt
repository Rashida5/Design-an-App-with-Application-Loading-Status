package com.udacity


sealed class ButtonState(var TextOnbutton:Int) {
    object Clicked : ButtonState(R.string.default_of_button)
    object Loading : ButtonState(R.string.button_loading)
    object Completed : ButtonState(R.string.default_of_button)
}
//we modify on the button state the string we need on button