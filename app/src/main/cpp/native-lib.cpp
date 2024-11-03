#include <jni.h>
#include <string>
#include <cstdlib>
#include <ctime>

extern "C" {

// Function for game logic
int game(char you, char computer) {
    if (you == computer)
        return -1;

    if (you == 's' && computer == 'p')
        return 0;
    else if (you == 'p' && computer == 's')
        return 1;

    if (you == 's' && computer == 'z')
        return 1;
    else if (you == 'z' && computer == 's')
        return 0;

    if (you == 'p' && computer == 'z')
        return 0;
    else if (you == 'z' && computer == 'p')
        return 1;

    return -1; // Draw
}

// JNI function to play the game and return the result
JNIEXPORT jstring JNICALL
Java_com_devstromo_rockpaperscissors_MainActivity_playGameJNI(JNIEnv *env, jobject /* this */, jchar playerChoice) {
    srand(time(NULL));
    char computerChoice;

    int n = rand() % 100;
    if (n < 33)
        computerChoice = 's';
    else if (n < 66)
        computerChoice = 'p';
    else
        computerChoice = 'z';

    int result = game(playerChoice, computerChoice);

    std::string outcome;
    if (result == -1) {
        outcome = "Game Draw!";
    } else if (result == 1) {
        outcome = "You won the game!";
    } else {
        outcome = "You lost the game!";
    }

    // Compose result message
    outcome += "\nYou chose: ";
    outcome += playerChoice;
    outcome += " and Computer chose: ";
    outcome += computerChoice;

    return env->NewStringUTF(outcome.c_str());
}

}