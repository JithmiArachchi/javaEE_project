package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@RequiredArgsConstructor
public class UserDto {
    private Long id;
    private String name ;
    private String email;
}
