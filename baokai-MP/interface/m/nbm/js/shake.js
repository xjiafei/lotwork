/**
 * Created by richardgong on 11/03/2016.
 */


var myShakeEvent = new Shake({
    threshold: 2, // optional shake strength threshold
    timeout: 100 // optional, determines the frequency of event generation
});

myShakeEvent.start();


window.addEventListener('shake', shakeEventDidOccur, false);

//function to call when shake occurs
function shakeEventDidOccur () {

    //put your own code here etc.
    alert('shake!');
}