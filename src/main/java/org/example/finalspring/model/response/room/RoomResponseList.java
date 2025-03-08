package org.example.finalspring.model.response.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalspring.entity.Room;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponseList {
    private List<RoomResponse> rooms;
    private long total;
}
