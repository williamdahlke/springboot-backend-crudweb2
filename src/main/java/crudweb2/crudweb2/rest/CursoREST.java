package crudweb2.crudweb2.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import crudweb2.crudweb2.model.Curso;
import crudweb2.crudweb2.repository.CursoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@CrossOrigin
@RestController
public class CursoREST {
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping("/cursos")
    public ResponseEntity<List<Curso>> getAllCursos() {
        List<Curso> list = cursoRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/cursos/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable int id) {
        Optional<Curso> op = cursoRepository.findById(id);
        if (op.isPresent()){
            return ResponseEntity.ok(op.get());
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PostMapping("/cursos")
    public ResponseEntity<Curso> insertCurso(@RequestBody Curso curso) {
        Optional<Curso> op = cursoRepository.findCursoByLink(curso.getLink());        
        if (op.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        else{
            curso.setId(-1);
            cursoRepository.save(curso);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }
    
    @PutMapping("/cursos/{id}")
    public ResponseEntity<Curso> updateCurso(@PathVariable int id, @RequestBody Curso curso) {
        Optional<Curso> op = cursoRepository.findById(id);
        if (op.isPresent()){
            curso.setId(id);
            cursoRepository.save(curso);
            return ResponseEntity.ok(curso);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(op.get());
        }
    }

    @DeleteMapping("/cursos/{id}")  
    public ResponseEntity<Curso> deleteCurso(@PathVariable int id){
        Optional<Curso> op = cursoRepository.findById(id);
        if (op.isPresent()){
            cursoRepository.delete(op.get());
            return ResponseEntity.ok(op.get());
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }    
}