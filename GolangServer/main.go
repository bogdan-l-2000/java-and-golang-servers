package main

import (
	//"encoding/json"
	"errors"
	"fmt"
	//"io/ioutil"
	// "log"
	"net/http"
	"os"
	//"path/filepath"
	//"strconv"
	//"sync"
)

type Page struct {
	Title string
	Body []byte
}

func loadHtmlPage(title string) (*Page, error) {
	filename := "static/html/" + title + ".html"
	fmt.Println(filename)
	body, err := os.ReadFile(filename)
	if err != nil {
		return nil, err
	}
	return &Page{Title: title, Body: body}, nil
}

func getView(w http.ResponseWriter, r *http.Request) {
	filename := "exampleView"
	p, _ := loadHtmlPage(filename)

	fmt.Fprintf(w, "%s", p.Body)
}



func main() {
	//http.HandleFunc("/", getRoot)
	http.HandleFunc("/view", getView)
	//http.HandleFunc("/posts/", postsHandler)

	fmt.Println("Server is running on localhost, port 8080")
	err := http.ListenAndServe(":8080", nil)
	if errors.Is(err, http.ErrServerClosed) {
		fmt.Println("Server closed")
	} else if err != nil {
		fmt.Println("Error starting server: %s", err)
		os.Exit(1)
	}
}