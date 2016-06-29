package santa.karma.player;

import santa.karma.ChaoticKarma;
import santa.karma.api.perk.KarmaPerkNegative;
import santa.karma.api.perk.KarmaPerkPositive;

import java.util.ArrayList;

public interface IPlayerData {
    /**
     * Gets the player's current karma.
     */
    int getKarma();

    /**
     * Gets the negative perks that the player has.
     */
    ArrayList<KarmaPerkNegative> getNegativePerks();

    /**
     * Gets the positive perks that the player has.
     */
    ArrayList<KarmaPerkPositive> getPositivePerks();

    /**
     * Sets the player's karma
     * @param value the karma value
     */
    void setKarma(int value);

    /**
     * Sets the player's negative perk list
     * @param value The value to set it as
     */
    void setNegativePerks(ArrayList<KarmaPerkNegative> value);

    /**
     * Sets the player's positive perk list
     * @param value The value to set it as
     */
    void setPositivePerks(ArrayList<KarmaPerkPositive> value);

    class DefaultImplemenation implements IPlayerData {
        private int karma = ChaoticKarma.DEFAULT_KARMA;
        private ArrayList<KarmaPerkNegative> negativePerks = new ArrayList<>();
        private ArrayList<KarmaPerkPositive> positivePerks = new ArrayList<>();

        @Override
        public int getKarma() {
            return karma;
        }

        @Override
        public ArrayList<KarmaPerkNegative> getNegativePerks() {
            return negativePerks;
        }

        @Override
        public ArrayList<KarmaPerkPositive> getPositivePerks() {
            return positivePerks;
        }

        @Override
        public void setKarma(int value) {
            karma = value;
        }

        @Override
        public void setNegativePerks(ArrayList<KarmaPerkNegative> value) {
            negativePerks = value;
        }

        @Override
        public void setPositivePerks(ArrayList<KarmaPerkPositive> value) {
            positivePerks = value;
        }
    }
}
