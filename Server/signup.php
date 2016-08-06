<?php
    header('Content-Type: application/json');

    include '../db_conn.php';



    $userId = $_POST['userId'];
    $userName = $_POST['userName'];
    $userCollage = $_POST['userCollage'];
    $userMajor = $_POST['userMajor'];
    $userSex = $_POST['userSex'];

    $sql = "SELECT * FROM `koraoke_user` WHERE `userId` = '$userId'";
    $result = mysql_query($sql);
    $num = mysql_num_rows($result);
    $row = mysql_fetch_array($result);
    $uid = $row['uid'];
    if(!($num == 0)){
        echo sprintf('{"result":"2","message":"%s"}',$uid);
        exit;
        return;
    }

    $now = date('Y-m-d H:i:s');
    $sql = "INSERT INTO `koraoke_user`(`uid`, `userId`, `userName`, `userCollage`, `userMajor`, `userSex`, `userType`, `userSign`, `userState`)
            VALUES (Null,'$userId','$userName','$userCollage','$userMajor','$userSex','N','$now','1')";
    $result = mysql_query($sql);

    if(!$result){
        echo sprintf('{"result":"0","message":"db_error"}');
        exit;
        return;
    }     


    echo sprintf('{"result":"1","message":"%d"}',mysql_insert_id());






?>