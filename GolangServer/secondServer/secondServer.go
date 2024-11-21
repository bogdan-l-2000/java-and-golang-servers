package main

import (
	//"errors"
	"fmt"
	"log"
	"net/http"
	// "os"
)

func Index(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "This is the Homepage.")
}

func Info(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "This is the Homepage.")
}

func main() {
	http.HandleFunc("/", Index)
	http.HandleFunc("/info", Info)

	err := http.ListenAndServe(":8080", nil)
	if err != nil {
		log.Fatal(err)
	}
}
