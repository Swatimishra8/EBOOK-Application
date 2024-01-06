<?php

    $password = $_GET['Password'];
	$hashed_password = password_hash($password , PASSWORD_BCRYPT);


		$con=new mysqli("192.168.16.68","SwatiRaksha","RakshaSwati@987","ebook");
		$st_check=$con->prepare("select * from userinformation where Email=?");
		$st_check->bind_param("s", $_GET["Email"]);
		$st_check->execute();
		$rs=$st_check->get_result();
		if($rs->num_rows==0)
		{	
		$st=$con->prepare("insert into userinformation (Username, Email, Password) values(?,?,?)");
	//	$st->bind_param("sss",$_GET["Username"],$_GET["Email"],$_GET["Password"]);
		$st->bind_param("sss",$_GET["Username"],$_GET["Email"], $hashed_password);
		$st->execute();
		echo "Successful!";
		}
		else
			echo "User already exists!";

?>