<?php
		$con=new mysqli("192.168.16.68","SwatiRaksha","RakshaSwati@987","ebook");
		//$st_check=$con->prepare("select * from userinformation where Email=? and Password=?");
		$st_check=$con->prepare("select * from userinformation where Email=?");
		//$st_check->bind_param("ss", $_GET["Email"],$_GET["Password"]);
		$st_check->bind_param("s", $_GET["Email"]);
		$st_check->execute();
		$rs=$st_check->get_result();
if ($rs->num_rows == 0) {
	echo "0";
} 
else {
	echo "1";
}

?>