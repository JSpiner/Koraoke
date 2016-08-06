<?php
    header('Content-Type: application/json');

    include '../db_conn.php';

    $sql = "SELECT * FROM `koraoke_song`
            WHERE `songState` = '1'";
    $result = mysql_query($sql);
    $data = array();
    while($row = mysql_fetch_assoc($result)){
            $data[] = $row;
    }

    echo json_encode($data);