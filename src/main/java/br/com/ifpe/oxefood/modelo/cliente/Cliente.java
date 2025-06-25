package br.com.ifpe.oxefood.modelo.cliente;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length; // Importar para @Length

import com.fasterxml.jackson.annotation.JsonFormat; // Importar para @CPF

import br.com.ifpe.oxefood.modelo.acesso.Usuario;
import br.com.ifpe.oxefood.util.entity.EntidadeAuditavel; // Pode ser útil se a entidade for serializada diretamente
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull; // Embora @NotBlank seja geralmente preferível para Strings
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Cliente")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends EntidadeAuditavel {

    @OneToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "cliente", orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<EnderecoCliente> enderecos;

    @Column(nullable = false, length = 100)
    @NotNull(message = "O Nome é de preenchimento obrigatório")
    @NotEmpty(message = "O Nome é de preenchimento obrigatório")
    @Length(max = 100, message = "O Nome deverá ter no máximo {max} caracteres")
    private String nome;

    @Column(nullable = false) // O campo dataNascimento não poderá ser nulo no DB
    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento não pode ser futura.")
    @JsonFormat(pattern = "dd/MM/yyyy") // Esta anotação é mais para serialização/desserialização, mas pode ser mantida
    private LocalDate dataNascimento;

    @Column(unique = true, nullable = false, length = 11) // Adicionei nullable=false e length=11
    @NotBlank(message = "O CPF é de preenchimento obrigatório")
    // @CPF(message = "CPF inválido")
    private String cpf;

    @Column

    private String foneCelular;

    @Column
    private String foneFixo;

}
