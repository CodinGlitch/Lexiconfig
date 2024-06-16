package com.codinglitch.lexiconfig.events;

import com.codinglitch.lexiconfig.LexiconEvent;

/**
 * This event is called during the cataloging process at the very start of the mod after all mods have shelved their lexicons.
 * <p>
 * When using in conjunction with {@code RevisionEvent}, the class can be used in the method in place of {@code CatalogEvent}.
 *
 * @see com.codinglitch.lexiconfig.events.RevisionEvent
 */
public class CatalogEvent extends RevisionEvent {
}
