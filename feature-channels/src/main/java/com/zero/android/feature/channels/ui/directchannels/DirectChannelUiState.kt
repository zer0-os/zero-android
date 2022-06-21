package com.zero.android.feature.channels.ui.directchannels

import com.zero.android.common.ui.Result
import com.zero.android.models.Channel

class DirectChannelUiState(
    channels: Result<List<Channel>>
) {
    val directChannels = if (channels is Result.Success) {
        channels.data
    } else emptyList()
}
