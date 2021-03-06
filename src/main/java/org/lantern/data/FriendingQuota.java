package org.lantern.data;

import javax.persistence.Id;

/**
 * <p>
 * Tracks how much a user has friended versus how much they're allowed to
 * friend.
 * </p>
 * 
 * <p>
 * FriendingQuota is the ancestor of {@link LanternFriend}, thereby grouping all
 * LanternFriends for a single {@link LanternUser} (key email) into a single entity
 * group that is separate from the LanternUser's entity group.
 * </p>
 */
public class FriendingQuota {
    @Id
    private String email;

    /**
     * The maximum number of friending operations allowed for this user.
     * 
     * Note - adding someone as a friend consumes this quota. Unfriending
     * doesn't give you a 2nd chance.
     */
    private int maxAllowed;

    /**
     * The total number of friending ops, including past friends even if they're
     * no longer friends.
     */
    private int totalFriended = 0;

    public FriendingQuota() {
    }

    public FriendingQuota(String email, int maxAllowed) {
        this.email = email;
        this.maxAllowed = maxAllowed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMaxAllowed() {
        return maxAllowed;
    }

    public void setMaxAllowed(int maxAllowed) {
        this.maxAllowed = maxAllowed;
    }

    public int getTotalFriended() {
        return totalFriended;
    }

    public void setTotalFriended(int totalFriended) {
        this.totalFriended = totalFriended;
    }

    /**
     * Check the quota increment it if still below quota.
     * 
     * @return true if we're below the quota, false otherwise
     */
    public boolean checkAndIncrementTotalFriended() {
        if ("test@test.com".equals(email) ||
                "brandnew@email.com".equals(email)) {
            // These are test email addresses, skip the check
            return true;
        }
        if (getRemainingQuota() <= 0) {
            return false;
        } else {
            this.totalFriended += 1;
            return true;
        }
    }

    /**
     * Calculates the number of remaining friends that this user is allowed to
     * add.
     * 
     * @return
     */
    public int getRemainingQuota() {
        return maxAllowed - totalFriended;
    }

}
