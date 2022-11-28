    let rollsleft = 3;
    let movesleft = 13;
    const controller = new AbortController;

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
	   	    if ( rollsleft != 3) {
                event.target.classList.toggle('hold');
	            die.hold = !die.hold;
	            let k = dice.indexOf(die);
	            $.ajax({
                    url: "http://localhost:9000/hold/" + k,
                    type: 'GET',
                    dataType: 'json',
                    success: function(res) { }
                });
            }
      });
  });

    let scoretable = [];
	$('.yourscore').each(function(){
	    let scoreelement = {element: this, fix: false};
	    scoreelement.element.style.color = "#A7ADC6";
	    scoreelement.element.style.cursor = "pointer";
	    scoretable.push(scoreelement);
	});
 for (let i = 6; i < 8; i++) {
       scoretable[i].fix = true;
	   scoretable[i].element.style.color = "#56667A";
	   scoretable[i].element.style.cursor = "auto";
  };
 for (let i = 15; i < 18; i++) {
       scoretable[i].fix = true;
	   scoretable[i].element.style.color = "#56667A";
	   scoretable[i].element.style.cursor = "auto";
  };
 addlisteners();

  $(document).ready(function(){
    $("#roll").click(function(e){
        e.preventDefault();
        if (rollsleft != 0) {
            dice.forEach(function(die){
                if (!die.hold) {
		            die.image.classList.add("shake");
		        };
		    });
            setTimeout(function(){
                dice.forEach(function(die){
		            if (!die.hold) {
                        die.image.classList.remove("shake");
                    };
                });
            }, 1000 );
	        $.ajax({
                url: "http://localhost:9000/roll",
                type: 'GET',
                dataType: 'json',
                success: function(res) {
        	        for (let i = 0; i < 5; i++) {
        		        dice[i].value = res[i];
        		        dice[i].image.setAttribute("src", images[res[i] -1]);
        		    };
                    rollsleft = res[5];
                    $("#turns").html(rollsleft);
                    countscore();
                }
    	    });
        }
    });


  });

function addlisteners() {
  for (let i = 0; i < 18; i++) {
        if (!scoretable[i].fix) {
            scoretable[i].element.addEventListener('click',function handleClick() {
               if ( rollsleft != 3) {
                scoretable[i].element.style.color = "#56667A";
                scoretable[i].element.style.cursor = "auto";
                scoretable[i].fix = true;
                scoretable[i].element.removeEventListener('click', arguments.callee, false);
	            $.ajax({
                    url: "http://localhost:9000/fix/" + i,
                    type: 'GET',
                    dataType: 'json',
                    success: function(res) {
                        movesleft = 13 - res;
                        $("#rounds").html(movesleft);
                        if (movesleft == 0) {
                            rollsleft = 0;
                            $("#turns").html(rollsleft);
                            finishgame();
                        }
                        else {
                            rollsleft = 3;
                            $("#turns").html(rollsleft);
                            continuegame();
                        }
                    }
                });
                };
            }, false);
        };
  }
}

function continuegame() {
    dice.forEach(function(die) {
	    if (die.hold) {
             die.image.classList.toggle('hold');
	         die.hold = !die.hold;
	    };
	});
	 $.ajax({
                    url: "http://localhost:9000/countfor0",
                    type: 'GET',
                    dataType: 'json',
                    success: function(res) {
                     for (let i = 0; i < 18; i++) {
                        scoretable[i].element.innerHTML = res[i];
                     }
                    }
                });
}

function countscore() {
   $.ajax({
     url: "http://localhost:9000/count",
     type: 'GET',
     dataType: 'json',
     success: function(res) {
         for (let i = 0; i < 18; i++) {
            scoretable[i].element.innerHTML = res[i];
            };
         if (res[18] == 1) {
             celebrateyahtzees();
         }
     }
   });
}

function finishgame() {
    $.ajax({
         url: "http://localhost:9000/countfor0",
         type: 'GET',
         dataType: 'json',
         success: function(res) {
             for (let i = 0; i < 18; i++) {
             scoretable[i].element.innerHTML = res[i];
             }
         }
    });
    var totalscore = 0;
    $.ajax({
        url: "http://localhost:9000/totalscore",
        type: 'GET',
        dataType: 'json',
        success: function(res) {
         totalscore = res;
         celebrateyahtzees();
	    var yahtzeetext = document.createElement('div');
        yahtzeetext.className = 'congrat';
	    yahtzeetext.innerHTML = "Game over! \n Your score is " + totalscore + "!";
	    yahtzeetext.style.top = 50 + 'px';
	    yahtzeetext.style.left = 50 + 'px';
	    document.getElementById("confetti-wrapper").appendChild(yahtzeetext);}
    })
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



