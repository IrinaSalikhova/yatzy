// dicing

let rollcounter = 0;

let images = ["../dice-1.png",
"../dice-2.png",
"../dice-3.png",
"../dice-4.png",
"../dice-5.png",
"../dice-6.png"];

const dice = [

   {value: 1, hold: false, image: document.querySelector("#die1") },
   {value: 1, hold: false, image: document.querySelector("#die2")},
   {value: 1, hold: false, image: document.querySelector("#die3")},
   {value: 1, hold: false, image: document.querySelector("#die4")},
   {value: 1, hold: false, image: document.querySelector("#die5")}
];
     
dice.forEach(function(die){
		
   die.image.setAttribute("src", images[die.value -1]);
   die.image.addEventListener("click",function handleClick(event) {
	   if (rollcounter != 0) {
    event.target.classList.toggle('hold');
	die.hold = !die.hold;
	   };
    });
	
	  });
	  
function rolldice(){
    dice.forEach(function(die){
        if (!die.hold) {
		die.image.classList.add("shake");
		die.value = die.value = Math.floor(Math.random()*6) +1;		};
    });
    setTimeout(function(){ 
    dice.forEach(function(die){
		if (!die.hold) {
        die.image.classList.remove("shake"); };
    });
	
	  dice.forEach(function(die){
		if (!die.hold) {
    
   die.image.setAttribute("src", images[die.value -1]);
	  };
	  });
  
},
    1000
    )
};

// reset dices after scoring
function setdice(){
	dice.forEach(function(die){
		die.value = 1;
		die.image.setAttribute("src", images[0]);
});
}


//button 

var modal = document.getElementById("aboutAssignment");
var btn = document.getElementById("About");
var span = document.getElementsByClassName("close")[0];

btn.onclick = function() {
	modal.style.display = "block";
  }
span.onclick = function() {
	modal.style.display = "none";
  }

// scoring

let movecounter = 0;

let aces = 0;
let twos = 0;
let threes = 0;
let fours = 0;
let fives = 0;
let sixes = 0;
let bonus35 = 0;
let uppertotal = 0;
let ofakind3 = 0;
let ofakind4 = 0;
let fullhouse = 0;
let smallstr = 0;
let largestr = 0;
let yahtzee = 0;
let chance = 0;
let bonusyahtzee = 0;
let lowertotal = 0;
let totaltotal = 0;

// Scoring display

let acesscore = document.getElementById("aces");
let twosscore = document.getElementById("twos");
let threesscore = document.getElementById("threes");
let foursscore = document.getElementById("fours");
let fivesscore = document.getElementById("fives");
let sixesscore = document.getElementById("sixes");
let bonus35score = document.getElementById("bonus35");
let uppertotalscore = document.getElementById("uppertotal");
let ofakind3score = document.getElementById("ofakind3");
let ofakind4score = document.getElementById("ofakind4");
let fullhousescore = document.getElementById("fullhouse");
let smallstrscore = document.getElementById("smallstr");
let largestrscore = document.getElementById("largestr");
let yahtzeescore = document.getElementById("yahtzee");
let chancescore = document.getElementById("chance");
let bonusyahtzeescore = document.getElementById("bonusyahtzee");
let lowertotalscore = document.getElementById("lowertotal");
let totaltotalscore = document.getElementById("totaltotal");


let roundsleft = document.getElementById("rounds");
let rollsleft = document.getElementById("turns");

setScore();

	// Make an array with the dices score
var dicesScores = Array(5);


// after rolling shows temporary possible results with listeners to click
$('#roll').click(function(){
	if (rollcounter < 3) {
	rolldice();
	rollcounter ++;
	rollsleft.innerHTML = 3 - rollcounter;
	takedices();
	addlisteners();
	countupper();
	countlower();
	setScore(); 
	};
});

function takedices() {
//cubes (will be taken from dices later)
for (let i = 0; i < 5; i++) {
dicesScores[i] = dice[i].value;

};
}

// when result chosed it saves to permanent memory, hides temporary result from field and continues game
function fixresult () {
	let score = this.innerHTML;
  this.parentElement.innerHTML = score;
  
  this.style.display = 'none';
  continuegame();
	
}
// delete listeners, count results, count move, checks if game finished, or wait for next roll
function continuegame() {
		deletelisteners();
	    setScoretoNull();
		counttotals();
		setdice();
		dice.forEach(function(die){
		if (die.hold)
		die.hold = !die.hold;
		die.image.classList.remove('hold');
	});
		rollcounter = 0;
		rollsleft.innerHTML = 3 - rollcounter;
	movecounter ++;
	roundsleft.innerHTML = 13 - movecounter;
	if (movecounter === 13) {
		finishgame();
	};
}

// animation for gameover
function finishgame() {
		celebrateyahtzees();
		var yahtzeetext = document.createElement('div');
  yahtzeetext.className = 'congrat';
	yahtzeetext.innerHTML = "Game over! \n Your score is " + totaltotal + "!";
	yahtzeetext.style.top = 50 + 'px';
	yahtzeetext.style.left = 50 + 'px';
	document.getElementById("confetti-wrapper").appendChild(yahtzeetext);
} 


// animation for yahtzee
function celebrateyahtzees() {
	setTimeout(function(){ 
    
    for(i=0; i<150; i++) {
  // Random rotation
  var randomRotation = Math.floor(Math.random() * 360);
    // Random Scale
  var randomScale = Math.random() * 1;
  // Random width & height between 0 and viewport
  var randomWidth = Math.floor(Math.random() * Math.max(document.documentElement.clientWidth, window.innerWidth || 0));
  var randomHeight =  Math.floor(Math.random() * Math.max(100));
  
  // Random animation-delay
  var randomAnimationDelay = Math.floor(Math.random() * 5);

  // Random colors
  var colors = ['#1A090D', '#A7ADC6', '#ED6A5A', '#56667A', '#F4F1BB']
  var randomColor = colors[Math.floor(Math.random() * colors.length)];

  // Create confetti piece
  var confetti = document.createElement('div');
  confetti.className = 'confetti';
  confetti.style.top=randomHeight + 'px';
  confetti.style.right=randomWidth + 'px';
  confetti.style.backgroundColor=randomColor;
  // confetti.style.transform='scale(' + randomScale + ')';
  confetti.style.obacity=randomScale;
  confetti.style.transform='skew(15deg) rotate(' + randomRotation + 'deg)';
  confetti.style.animationDelay=randomAnimationDelay + 's';
  document.getElementById("confetti-wrapper").appendChild(confetti);
}
	var yahtzeetext = document.createElement('div');
  yahtzeetext.className = 'congrat';
	yahtzeetext.innerHTML = "Yahtzee!";
	yahtzeetext.style.top = 300 + 'px';
	yahtzeetext.style.left = 150 + 'px';
	document.getElementById("confetti-wrapper").appendChild(yahtzeetext);

} , 1200 );
};

// return the value of bonus and score elements
function counttotals() {
// count uppertotal
	const yourscoresupper = document.querySelectorAll(".upper");
uppertotal = 0;
	for (var i = 0; i < yourscoresupper.length; i++) {
		let x = parseInt(yourscoresupper[i].innerHTML);
		if (Number.isInteger(x)) {
		uppertotal += x;
		}
	}
		bonus35 = uppertotal >= 63 ? 35 : 0;
		uppertotal += bonus35;	
		
// count lowertotal	
	const yourscoreslower = document.querySelectorAll(".lower");
lowertotal = 0;
	for (var i = 0; i < yourscoreslower.length; i++) {
		let x = parseInt(yourscoreslower[i].innerHTML);
		if (Number.isInteger(x)) {
		lowertotal += x;
		}
	}
lowertotal += bonusyahtzee;
// count total ant set results
	totaltotal = uppertotal + lowertotal;
	bonus35score.innerHTML = bonus35;
	uppertotalscore.innerHTML = uppertotal;
	lowertotalscore.innerHTML = lowertotal;
	totaltotalscore.innerHTML = totaltotal;
}

// Return the values of upper section elements
function countupper() {
	aces = sumDuplicates(1, dicesScores);
	twos = sumDuplicates(2, dicesScores);
    threes = sumDuplicates(3, dicesScores);
	fours = sumDuplicates(4, dicesScores);
	fives = sumDuplicates(5, dicesScores);
	sixes = sumDuplicates(6, dicesScores);

}

// return the values of lower section elements 
function countlower() {
// common elements	
var count = {};
dicesScores.forEach(function(i) { count[i] = (count[i]||0) + 1;});
var repeaters = Object.values(count);

// 3 of a kind
const checkfor3 = (element) => element > 2;
ofakind3 = repeaters.some(checkfor3) ? countall() : 0;

// 4 of a kind
const checkfor4 = (element) => element > 3;
ofakind4 = repeaters.some(checkfor4) ? countall() : 0;

// full house
const checkforfh = (element) => element == 3;
fullhouse = repeaters.length === 2 && repeaters.some(checkforfh) ? 25 : 0;

// small straight
if (repeaters.length >= 4) {
	if ((dicesScores.includes(1) && dicesScores.includes(2) && dicesScores.includes(3) && dicesScores.includes(4)) || (dicesScores.includes(2) && dicesScores.includes(3) && dicesScores.includes(4) && dicesScores.includes(5)) || (dicesScores.includes(3) && dicesScores.includes(4) && dicesScores.includes(5) && dicesScores.includes(6))) {
		smallstr = 30;
	};
};

// large straight
const checkforlstr1 = (element) => element === 1;
const checkforlstr6 = (element) => element === 6;
largestr = (repeaters.length === 5) &&  (dicesScores.some(checkforlstr1) || dicesScores.some(checkforlstr6)) && (dicesScores.some(checkforlstr1) != dicesScores.some(checkforlstr6)) ? 40 : 0; 

// yahtzee
if (repeaters.length === 1) {
yahtzee = 50;
celebrateyahtzees();
}

// chance
chance = countall();

// yahtzee bonus 
let checkya = document.getElementById("checkya");

if (repeaters.length === 1 && checkya.innerHTML == 50) {
bonusyahtzee += 100;
celebrateyahtzees();
bonusyahtzeescore.innerHTML = bonusyahtzee;
}

}

// sum all array values
function countall() {
	let count = 0;
	for (let i = 0; i < dicesScores.length; i++) {
		count += dicesScores[i];
		}
	return count ;
}

// Return the sum of specified duplicate values in an array
function sumDuplicates(value, array) {
	let count = 0;
	for (let i = 0; i < array.length; i++) {
		if (array[i] == value) {
			count++;
		}
	}
	return count * value;
}

// Set the average results in the table

function setScore() {
	setTimeout(function(){ 
	acesscore.innerHTML = aces;
	twosscore.innerHTML = twos;
	threesscore.innerHTML = threes;
	foursscore.innerHTML = fours;
	fivesscore.innerHTML = fives;
	sixesscore.innerHTML = sixes;
	bonus35score.innerHTML = bonus35;
	
	ofakind3score.innerHTML = ofakind3;
	ofakind4score.innerHTML = ofakind4;
	fullhousescore.innerHTML = fullhouse;
	smallstrscore.innerHTML = smallstr;
	largestrscore.innerHTML = largestr;
	yahtzeescore.innerHTML = yahtzee;
	chancescore.innerHTML = chance;
	bonusyahtzeescore.innerHTML = bonusyahtzee;
} , 1200 );
}

//adds listeners to score values
function addlisteners() {
acesscore.addEventListener("click", fixresult, false);
twosscore.addEventListener("click", fixresult, false);
threesscore.addEventListener("click", fixresult, false);
foursscore.addEventListener("click", fixresult, false);
fivesscore.addEventListener("click", fixresult, false);
sixesscore.addEventListener("click", fixresult, false);
ofakind3score.addEventListener("click", fixresult, false);
ofakind4score.addEventListener("click", fixresult, false);
fullhousescore.addEventListener("click", fixresult, false);
smallstrscore.addEventListener("click", fixresult, false);
largestrscore.addEventListener("click", fixresult, false);
yahtzeescore.addEventListener("click", fixresult, false);
chancescore.addEventListener("click", fixresult, false);

}

//delete listeners (after fixing the score)
function deletelisteners() {
	acesscore.removeEventListener("click", fixresult, false);
	twosscore.removeEventListener("click", fixresult, false); 
	threesscore.removeEventListener("click", fixresult, false); 
	foursscore.removeEventListener("click", fixresult, false); 
	fivesscore.removeEventListener("click", fixresult, false); 
	sixesscore.removeEventListener("click", fixresult, false); 
	ofakind3score.removeEventListener("click", fixresult, false);
	ofakind4score.removeEventListener("click", fixresult, false);
	fullhousescore.removeEventListener("click",fixresult, false);
	smallstrscore.removeEventListener("click", fixresult, false);
	largestrscore.removeEventListener("click", fixresult, false);
	yahtzeescore.removeEventListener("click", fixresult, false);
	chancescore.removeEventListener("click", fixresult, false);
}

//nulls score elements
function setScoretoNull() {

 aces = 0;
 twos = 0;
 threes = 0;
 fours = 0;
 fives = 0;
 sixes = 0;
 ofakind3 = 0;
 ofakind4 = 0;
 fullhouse = 0;
 smallstr = 0;
 largestr = 0;
 yahtzee = 0;
 chance = 0;

}