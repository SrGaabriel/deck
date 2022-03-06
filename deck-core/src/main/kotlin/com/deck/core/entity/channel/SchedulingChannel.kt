package com.deck.core.entity.channel

import com.deck.core.entity.Entity
import com.deck.core.stateless.channel.StatelessScheduleAvailability
import com.deck.core.stateless.channel.StatelessSchedulingChannel

public interface SchedulingChannel: TeamChannel, StatelessSchedulingChannel

public interface ScheduleAvailability: Entity, StatelessScheduleAvailability