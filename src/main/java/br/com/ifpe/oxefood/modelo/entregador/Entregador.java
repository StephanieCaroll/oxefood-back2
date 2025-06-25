package br.com.ifpe.oxefood.modelo.entregador;

import java.time.LocalDate;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.oxefood.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min; // Importe para validar dígitos
import jakarta.validation.constraints.NotBlank; // Importe para valor mínimo
import jakarta.validation.constraints.NotNull; // Importe para não ser vazio ou null
import jakarta.validation.constraints.Past; // Importe para não ser null
import jakarta.validation.constraints.Pattern; // Importe para datas no passado
import jakarta.validation.constraints.Size; // Importe para regex (padrão)
import lombok.AllArgsConstructor; // Importe para tamanho de String
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Entregador")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entregador extends EntidadeAuditavel {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Column
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    // @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}", message = "O CPF deve estar no formato 000.000.000-00 ou 00000000000.")
    @Column(unique = true) // CPF geralmente é único
    private String cpf;

    @NotBlank(message = "O RG é obrigatório.")
    @Size(max = 20, message = "O RG deve ter no máximo 20 caracteres.")
    @Column(unique = true) // RG geralmente é único
    private String rg;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento não pode ser futura.")
    @Column
    private LocalDate dataNascimento;

    @NotBlank(message = "O telefone celular é obrigatório.")
    // @Pattern(regexp = "^81\\d{9}$", message = "O telefone celular deve começar com '81' e ter 11 dígitos (ex: 81987654321).")
    @Column
    private String foneCelular;

//    @Pattern(regexp = "^\\d{10,11}$|^$", message = "O telefone fixo deve ter 10 ou 11 dígitos numéricos ou ser vazio.") // Permite vazio ou com dígitos
    @Column
    private String foneFixo;

    @NotNull(message = "A quantidade de entregas realizadas é obrigatória.")
    @Min(value = 0, message = "A quantidade de entregas realizadas não pode ser negativa.")
    @Column
    private Integer qtdEntregasRealizadas;

    @NotNull(message = "O valor do frete é obrigatório.")
    @Min(value = 0, message = "O valor do frete não pode ser negativo.")
    @Column
    private Double valorFrete;

    @NotBlank(message = "A rua do endereço é obrigatória.")
    @Size(max = 150, message = "A rua do endereço não pode exceder 150 caracteres.")
    @Column
    private String enderecoRua;

    @Size(max = 100, message = "O complemento do endereço deve ter no máximo 100 caracteres.")
    @Column
    private String enderecoComplemento;

    @NotBlank(message = "O número do endereço é obrigatório.")
    @Size(max = 20, message = "O número do endereço deve ter no máximo 20 caracteres.")
    @Column
    private String enderecoNumero;

    @NotBlank(message = "O bairro do endereço é obrigatório.")
    @Size(max = 100, message = "O bairro do endereço deve ter no máximo 100 caracteres.")
    @Column
    private String enderecoBairro;

    @NotBlank(message = "A cidade do endereço é obrigatória.")
    @Size(max = 100, message = "A cidade do endereço deve ter no máximo 100 caracteres.")
    @Column
    private String enderecoCidade;

    @NotBlank(message = "O CEP do endereço é obrigatório.")
    // @Pattern(regexp = "\\d{8}", message = "O CEP deve conter 8 dígitos numéricos.")
    @Column
    private String enderecoCep;

    @NotBlank(message = "A UF do endereço é obrigatória.")
    @Size(min = 2, max = 2, message = "A UF deve ter 2 caracteres (Ex: PE, SP).")
    @Column
    private String enderecoUf;

    @Column
    private Boolean ativo;
}