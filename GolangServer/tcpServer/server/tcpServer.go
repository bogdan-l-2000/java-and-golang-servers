package main

import (
	"fmt"
	"net"
)

func main() {
	ln, err := net.Listen("tcp", ":8080")
	if err != nil {
		fmt.Println(err)
		return
	}

	for {
		conn, err := ln.Accept()
		if err != nil {
			fmt.Println(err)
			continue
		}

		// Handle the connection in a new goroutine
		go handleConnection(conn)
	}
}

func handleConnection(conn net.Conn) {
	// defer keyword is used to execute code after a function finishes
	defer conn.Close()

	//
	buf := make([]byte, 1024)
	n, err := conn.Read(buf)
	if err != nil {
		fmt.Println(err)
		return
	}

	// Print the incoming data
	defer fmt.Printf("Received: %s\n", buf[:n])

	_, err = conn.Write([]byte("Hello to you, Client!"))
	if err != nil {
		fmt.Println(err)
		return
	}
}
