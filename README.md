Make an Android app with two activities:

The first activity (equivalent to the first screen which will appear when opening the application) will be a splash screen,
which will contain an image at the choice of the student, the name of the student implementing the application and the group from which he is
part. The splash screen will be displayed for 3 seconds, then it will go to the main activity of the application.
The main activity will be to make an anagram of a String entered by the user. To make the anagram, the alphabet will be taken into account
of the English language, and not of the Romanian language (thus eliminating the special characters of the Romanian language: ă,
â, ț, ş, î).

The main activity should contain a button with the text Create anagram. When this button is pressed, an AlertDialog will be displayed on the screen, which will contain an EditText control and two buttons with the text Cancel and Create. The AlertDialog will have the title "Create the anagram". Pressing the button in the AlertDialog with the text Create will create an anagram of the String entered by the user, close the AlertDialog, and display in a TextView control the original text entered by the user and the resulting anagram. When pressing the button with the text Cancel, the AlertDialog will close without the anagram creation operation being executed.

Note: Anagram is a word/phrase that is obtained by changing the order of the letters of one another word or phrase.
Example (in English):
• dusty = study
• cat = act
• states = keys
(These examples can be used both as functional test models and as models for the way the result of clicking the Create button should be displayed on the main screen)
