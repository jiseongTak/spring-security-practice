package practice.security.note;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.security.user.User;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUserOrderByIdDesc(User user);

    Note findByIdAndUser(Long id, User user);
}