<?php

	$conn = new mysqli("192.168.16.68", "SwatiRaksha", "RakshaSwati@987", "ebook");

	if ($conn->connect_error){
     die("Connection failed: " . $conn->connect_error);
 }

    //retrieve email 
    $email = $_GET["Email"];

	$sql = "SELECT Username FROM userinformation WHERE Email = '$email' ";
	$res = mysqli_query($conn, $sql);

	if ($row = mysqli_fetch_assoc($res)) {
		$name = $row["Username"];

		//pass to app 
	    echo $name;
	}
	else{
	echo "Error - something went wrong!!";
	}
	
	?>