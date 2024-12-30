package cmd

import (
	"fmt"
	"net/http"

	"github.com/inqast/cli/internal"
	"github.com/inqast/cli/internal/client"
	"github.com/spf13/cobra"
)

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

		c := client.New(internal.LinkBase, internal.ServerAddr, &http.Client{})

		link, err := c.Get(args[0])
		if err != nil {
			fmt.Println(err.Error())
			return
		}

		fmt.Println(link)
	},
}

func init() {
	rootCmd.AddCommand(getCmd)
}
