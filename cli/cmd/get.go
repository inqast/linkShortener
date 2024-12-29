package cmd

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"

	"github.com/inqast/cli/internal/short"
	"github.com/spf13/cobra"
	"github.com/spf13/viper"
)

type getResp struct {
	Link string `json:"link"`
}

const (
	shortLink = "shortLink"
)

// getCmd represents the get command
var getCmd = &cobra.Command{
	Use:       "get",
	Short:     "get original link by short one",
	ValidArgs: []string{shortLink},
	Run: func(cmd *cobra.Command, args []string) {
		if len(args) != 1 {
			fmt.Printf("missing argument: shortLink")
			return
		}

		hash, err := short.GetHashFromShortLink(args[0], viper.GetString("linkBase"))
		if err != nil {
			fmt.Printf("error parsing short link: %s", err.Error())
			return
		}

		base := viper.GetString("serverAddr")
		url := fmt.Sprintf("http://%s/link/%d", base, hash)
		fmt.Println(url)

		c := http.Client{}
		resp, err := c.Get(url)
		if err != nil {
			fmt.Printf("Error %s\n", err)
			return
		}
		defer resp.Body.Close()

		body, err := io.ReadAll(resp.Body)

		r := &getResp{}
		if err := json.Unmarshal(body, r); err != nil {
			fmt.Println(body)
		}

		fmt.Println(r.Link)
	},
}

func init() {
	rootCmd.AddCommand(getCmd)
}
