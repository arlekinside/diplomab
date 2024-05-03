import Params from "../../Params";

function DarkText(props: { children : any} ) {
    return (
        <p style={{color: Params.colors.main, marginBlockEnd: "1vh", marginBlockStart: "2em", padding: "0 10px 0 10px"}}>
            {props.children}
        </p>
    )
}

export default DarkText;
