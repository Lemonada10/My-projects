
function getCurrentDate() {
    const time = new Date();

    const days= ["Monday" , "Tuesday" , "Wednedsay" , "Thursday" , "Friday" , "Saturday" , "Sunday"];
    const months= ["January","February","March","April","May","Juin","July","August","Septembre","October","November","December"]

    var dayOfWeek= days[time.getDay()];
    var month= months[time.getMonth()];
    var date= time.getDate();
    var year= time.getFullYear();

    var hours = time.getHours();
    var minutes = time.getMinutes();
    var seconds = time.getSeconds();

    minutes = minutes < 10 ? '0' + minutes : minutes;
    seconds = seconds < 10 ? '0' + seconds : seconds;


    const formattedDate = " | " + dayOfWeek + ", " + month + " " + date + ", " + year + " | " + "              " + hours + ":" + minutes + ":" + seconds;
    document.getElementById("time").innerHTML = formattedDate;
    
    return formattedDate;

}

getCurrentDate();
setInterval(getCurrentDate, 1000);

document.getElementById()

function give_away() {

    var emailRegex = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;

    
    var message= "";
    var email_message= "";
    var result= true;
    var email = document.getElementById("email").value;

    if((!document.getElementById("Cat2").checked === "" && !document.getElementById("Dog2").checked === "" && !document.getElementById("Mixed2").checked === "" ) || (!document.getElementById("Male2").checked === "" && !document.getElementById("Female2").checked === "") || (!document.getElementById("Dogs2").checked === "" && !document.getElementById("Cats2").checked === "" && !document.getElementById("Children").checked === "" && !document.getElementById("AllOfThem").checked === "" && !document.getElementById("None").checked === "") || (document.getElementById("Firstname").value.trim() === "" || document.getElementById("Lastname").value.trim() === "" || document.getElementById("Email").value === "" )) {
        message= "Please fill in all the boxes";
        document.getElementById("error_message").innerHTML = message;
        result = false;
        return result;
    } 
    else {
        
        document.getElementById("error_message").innerHTML = "";
    }

    if (!emailRegex.test(email) && email != "") {

        email_message = "Please enter a valid email address.";
        document.getElementById("email_error").innerHTML = email_message;
        result = false;
        return result;
    } 
    else {
        
        document.getElementById("email_error").innerHTML = "";
    }
   return result;
} 

// document.getElementById("form").addEventListener("submit", give_away);

function FindDogCat() {

    var message2= "";

    if((!document.getElementById("Cat").checked === "" || !document.getElementById("Dog").checked === "") || (document.getElementById("text").value === "" || !document.getElementById("otherbreed").checked === "") || (!document.getElementById("Male").checked === "" || !document.getElementById("Female").checked === "" || !document.getElementById("Whatever").checked === "" )|| (!document.getElementById("Dogs").checked === "" || !document.getElementById("Cats").checked === "" || !document.getElementById("Children").checked === "" || !document.getElementById("Whatever2").checked === "")) {
        message2= "Please fill in all the boxes";
        document.getElementById("error_message2").innerHTML = message2;
        return false;
    } else {
        message2 = ""; 
       
    }
}




