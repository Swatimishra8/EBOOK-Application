<?php
$conn = mysqli_connect("192.168.16.68", "SwatiRaksha", "RakshaSwati@987", "ebook");
  if (!$conn){
     die("Connection failed: " . mysqli_connect_error());
 }

 $sql = "SELECT BookName , BookCategory , BookDescription , PdfUrl , PdfIconUrl, BookAuthor FROM repository";
 $result = mysqli_query($conn , $sql);

 if(mysqli_num_rows($result)>0){
   //output data of each row as json
   $response = array();
   while($row = mysqli_fetch_assoc($result)){
    $response[] = $row;
   }

   echo json_encode($response);
 }

 else{
     echo "0 results";
 }

//mysqli_close($conn);

?>