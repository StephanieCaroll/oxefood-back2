package br.com.ifpe.oxefood.api.cliente;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import br.com.ifpe.oxefood.modelo.acesso.Perfil;
import br.com.ifpe.oxefood.modelo.acesso.Usuario;
import br.com.ifpe.oxefood.modelo.cliente.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    @NotNull(message = "O Nome é de preenchimento obrigatório")
    @NotEmpty(message = "O Nome é de preenchimento obrigatório")
    @Length(max = 100, message = "O Nome deverá ter no máximo {max} caracteres")
    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento não pode ser futura.")
    private LocalDate dataNascimento;

    @NotBlank(message = "O CPF é de preenchimento obrigatório")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "O Telefone Celular é de preenchimento obrigatório")
    private String foneCelular;

    private String foneFixo;

    @NotBlank(message = "O e-mail é de preenchimento obrigatório")
    @Email
    private String email;

    @NotBlank(message = "A senha é de preenchimento obrigatório")
    private String password;

    public Usuario buildUsuario() {
        return Usuario.builder()
                .username(email)
                .password(password)
                .roles(Arrays.asList(new Perfil(Perfil.ROLE_CLIENTE)))
                .build();
    }

    public Cliente build() {

        String cpfLimpo = (this.cpf != null) ? this.cpf.replaceAll("[^0-9]", "") : null;

        // Limpa o foneCelular removendo todos os caracteres não numéricos.
        String foneCelularLimpo = (this.foneCelular != null) ? this.foneCelular.replaceAll("[^0-9]", "") : null;

        // Limpa o foneFixo removendo todos os caracteres não numéricos.
        String foneFixoLimpo = (this.foneFixo != null) ? this.foneFixo.replaceAll("[^0-9]", "") : null;

        return Cliente.builder()
                .usuario(buildUsuario())
                .nome(nome)
                .dataNascimento(dataNascimento)
                .cpf(cpfLimpo)
                .foneCelular(foneCelularLimpo)
                .foneFixo(foneFixoLimpo)
                .build();
    }
}
