package cmd

import (
	"fmt"
	"net/http"

	"github.com/inqast/cli/internal"
	"github.com/inqast/cli/internal/client"
	"github.com/spf13/cobra"
)

// indexCmd represents the index command
var indexCmd = &cobra.Command{
	Use:       "index",
	Short:     "get list of created links for user, requires login",
	ValidArgs: []string{"userUUID"},
	Run: func(cmd *cobra.Command, args []string) {
		if len(args) != 1 {
			fmt.Printf("missing argument: userUUID")
			return
		}

		c := client.New(internal.LinkBase, internal.ServerAddr, &http.Client{})

		links, err := c.GetAll(args[0])
		if err != nil {
			fmt.Println(err.Error())
			return
		}

		for _, link := range links {
			fmt.Printf("%+v\n", link)
		}
	},
}

func init() {
	rootCmd.AddCommand(indexCmd)
}
