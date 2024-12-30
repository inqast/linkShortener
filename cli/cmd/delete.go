package cmd

import (
	"fmt"
	"net/http"

	"github.com/inqast/cli/internal"
	"github.com/inqast/cli/internal/client"
	"github.com/spf13/cobra"
)

// deleteCmd represents the delete command
var deleteCmd = &cobra.Command{
	Use:       "delete",
	Short:     "delete short link",
	ValidArgs: []string{"shortLink"},
	Run: func(cmd *cobra.Command, args []string) {
		if len(args) != 1 {
			fmt.Printf("missing argument: shortLink")
			return
		}

		user, _ := cmd.Flags().GetString(userKey)

		c := client.New(internal.LinkBase, internal.ServerAddr, &http.Client{})

		err := c.Delete(
			args[0],
			user,
		)
		if err != nil {
			fmt.Println(err.Error())
			return
		}
	},
}

func init() {
	rootCmd.AddCommand(deleteCmd)
}
