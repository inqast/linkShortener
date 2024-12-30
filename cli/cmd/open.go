package cmd

import (
	"fmt"
	"net/http"

	"github.com/inqast/cli/internal"
	"github.com/inqast/cli/internal/client"
	linkUtils "github.com/inqast/cli/internal/link"
	"github.com/spf13/cobra"
)

const (
	shortLink = "shortLink"
)

// openCmd represents the get command
var openCmd = &cobra.Command{
	Use:       "open",
	Short:     "open original link by short one",
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

		err = linkUtils.OpenLink(link)
		if err != nil {
			fmt.Println(err.Error())
			return
		}
	},
}

func init() {
	rootCmd.AddCommand(openCmd)
}
