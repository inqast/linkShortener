package cmd

import (
	"fmt"
	"net/http"

	"github.com/inqast/cli/internal"
	"github.com/inqast/cli/internal/client"
	"github.com/spf13/cobra"
)

// createCmd represents the create command
var createCmd = &cobra.Command{
	Use:       "create",
	Short:     "create short link",
	ValidArgs: []string{"originalLink"},
	Run: func(cmd *cobra.Command, args []string) {
		if len(args) != 1 {
			fmt.Printf("missing argument: originalLink")
			return
		}

		user, _ := cmd.Flags().GetString(userKey)
		limit, _ := cmd.Flags().GetString(limitKey)
		deadline, _ := cmd.Flags().GetString(deadlineKey)

		c := client.New(internal.LinkBase, internal.ServerAddr, &http.Client{})

		link, user, err := c.Create(
			args[0],
			user,
			limit,
			deadline,
		)
		if err != nil {
			fmt.Println(err.Error())
			return
		}

		fmt.Println("Link:", link)
		fmt.Println("User:", user)
	},
}

func init() {
	rootCmd.AddCommand(createCmd)
}
